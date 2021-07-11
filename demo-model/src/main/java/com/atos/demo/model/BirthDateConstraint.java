package com.atos.demo.model;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Annotation for a custom constraint
 */
@Documented
@Constraint(validatedBy = BirthDateValidator.class)
@Target( { METHOD, FIELD })
@Retention(RUNTIME)
public @interface BirthDateConstraint {
    String message() default "Invalid birthDate";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}