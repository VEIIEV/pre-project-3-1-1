package org.example.preproject231.validation.annotation;


import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.example.preproject231.validation.ExistInDbValidator;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Target({FIELD, PARAMETER, TYPE})
@Retention(RUNTIME)
@Documented
@Constraint(validatedBy = {ExistInDbValidator.class})
public @interface ExistInDb {

    String message() default "Система не может распознать переданную роль";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
