package com.atos.demo.api;

import javax.validation.Valid;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.atos.demo.model.User;
import com.atos.demo.port.IDao;

/**
 * Rest Api with 2 verbs
 */
@Controller
@RequestMapping(value="/user")
public class UserController {
	
	private static final org.slf4j.Logger LOGGER = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	public IDao dao;

	/**
	 * User lookup by login key
	 * @param login
	 * @return a user
	 */
    @GetMapping(value = "/{login}", produces="application/json")
    public @ResponseBody ResponseEntity<User> getUser(@PathVariable String login) {
	    var userOpt = dao.getUser(login);
	    if (userOpt.isPresent()) {
	    	return ResponseEntity.ok().body(userOpt.get());
	    }
	    else {
	    	return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	    }
    }

	/**
	 * Save a user (validation is applied)
	 * @param user
	 * @param simulation (facultative)
	 * @return
	 */
    @PostMapping(produces="application/json")
    public ResponseEntity<User> saveUser(@RequestBody @Valid User user, @RequestParam(required = false, defaultValue = "false") Boolean simulation) {
    	if (simulation) {
    		LOGGER.info("simulation !");
    		return new ResponseEntity<>(null, HttpStatus.ACCEPTED);
    	}
    	else {
	    	try {
	    		dao.saveUser(user);
	    		return new ResponseEntity<>(user, HttpStatus.CREATED);
	    	}
	    	catch (Exception e) {
	    		return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
	    	}
    	}
    }

}
