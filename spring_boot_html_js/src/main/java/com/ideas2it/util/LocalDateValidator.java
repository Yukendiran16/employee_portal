package com.ideas2it.util;

import org.apache.commons.validator.routines.DateValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class LocalDateValidator implements ConstraintValidator<UserDate, String> {
    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        boolean bool = true;
        try {
            LocalDate currentDate = LocalDate.now();
            int currentYear = currentDate.getYear();
            String[] date_Array = date.split("-");
            int year = Integer.parseInt(date_Array[0]);
            if ((currentYear - 60) <= year && year <= (currentYear - 18)) {
                bool = DateValidator.getInstance().isValid(date, "yyyy-MM-dd");
            }
            return bool;
        } catch (NumberFormatException | ArrayIndexOutOfBoundsException e) {
            return false;
        }
    }
}