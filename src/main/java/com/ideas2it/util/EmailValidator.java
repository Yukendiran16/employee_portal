package com.ideas2it.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        return org.apache.commons.validator.routines.EmailValidator.getInstance().isValid(email);
    }
}
