package com.atos.demo.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.validation.ConstraintViolationException;

import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.atos.demo.port.DaoException;

/**
 * Exception interceptor, format responses errors in a common way.
 */
@ControllerAdvice
public class ExceptionController {

	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(ExceptionController.class);

	/**
	 * run time exceptions from persistence
	 */
	@ExceptionHandler(DaoException.class)
	@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR)
	@ResponseBody
	public ArrayList<String> handleDataAccessException(Throwable ex) {
		var details = new ArrayList<String>();
		details.add(ex.getLocalizedMessage());
		LOGGER.error(ex.getLocalizedMessage());
		return details;
	}

	/**
	 * validation exceptions
	 */
	@ExceptionHandler(ConstraintViolationException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> onConstraintValidationException(ConstraintViolationException e) {
	    var errors = new HashMap<String, String>();
		for (var violation : e.getConstraintViolations()) {
			errors.put(violation.getPropertyPath().toString(), violation.getMessage());
		}
		return errors;
	}

	/**
	 * validation exceptions
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ResponseBody
	public Map<String, String> onMethodArgumentNotValidException(MethodArgumentNotValidException e) {
	    var errors = new HashMap<String, String>();
	    e.getBindingResult().getAllErrors().forEach((error) -> {
	        var fieldName = ((FieldError) error).getField();
	        var errorMessage = error.getDefaultMessage();
	        errors.put(fieldName, errorMessage);
	    });
	    return errors;
	}
	
}
