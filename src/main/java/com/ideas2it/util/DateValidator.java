package com.ideas2it.util;

import org.apache.commons.validator.routines.RegexValidator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static com.ideas2it.util.Constant.MONTH_28_DAYS;
import static com.ideas2it.util.Constant.MONTH_29_DAYS;
import static com.ideas2it.util.Constant.MONTH_30_DAYS;
import static com.ideas2it.util.Constant.MONTH_31_DAYS;

public class DateValidator implements ConstraintValidator<UserDate, String> {
    @Override
    public boolean isValid(String date, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        String[] date_array = date.split("-");
        System.out.println(Integer.parseInt(date_array[2]));
        if (Integer.parseInt(date_array[0]) >= (year - 50) &&
                Integer.parseInt(date_array[0]) <= (year - 18) &&
        31 == Integer.parseInt(date_array[2])) {
            System.out.println(Integer.parseInt(date_array[2]));
           return new RegexValidator(MONTH_31_DAYS).isValid(date);
        } else if (Integer.parseInt(date_array[0]) >= (year - 50) &&
                Integer.parseInt(date_array[0]) <= (year - 18) &&
                30 == Integer.parseInt(date_array[2])) {
            System.out.println(Integer.parseInt(date_array[2]));
            return new RegexValidator(MONTH_30_DAYS).isValid(date);
        } else if (Integer.parseInt(date_array[0]) >= (year - 50) &&
                Integer.parseInt(date_array[0]) <= (year - 18) &&
                29 == Integer.parseInt(date_array[2])) {
            System.out.println(Integer.parseInt(date_array[2]));
            return new RegexValidator(MONTH_29_DAYS).isValid(date);
        } else if (Integer.parseInt(date_array[0]) >= (year - 50) &&
                Integer.parseInt(date_array[0]) <= (year - 18) &&
                28 == Integer.parseInt(date_array[2])) {
            System.out.println(Integer.parseInt(date_array[2]));
            return new RegexValidator(MONTH_28_DAYS).isValid(date);
        } else {
            System.out.println(Integer.parseInt(date_array[2]));
            return false;
        }
    }
}