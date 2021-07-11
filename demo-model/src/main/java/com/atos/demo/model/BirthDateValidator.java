package com.atos.demo.model;

import java.time.ZonedDateTime;
import java.util.Date;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * A custom validator checking that a date is more than 18 years before now.
 */
public class BirthDateValidator implements ConstraintValidator<BirthDateConstraint, Date> {

	@Override
	public boolean isValid(Date birthDate, ConstraintValidatorContext cxt) {
		if (birthDate == null) {
			return false;
		}
		else {
			var nowless18y = ZonedDateTime.now().minusYears(18).toInstant();
			var morethan18yo = birthDate.toInstant().isBefore(nowless18y);
			return morethan18yo;
		}
	}

}