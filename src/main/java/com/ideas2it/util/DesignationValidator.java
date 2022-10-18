package com.ideas2it.util;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.List;

import static com.ideas2it.util.Constant.ROLE_1;
import static com.ideas2it.util.Constant.ROLE_2;
import static com.ideas2it.util.Constant.ROLE_3;


public class DesignationValidator implements ConstraintValidator<Designation, String> {
    @Override
    public boolean isValid(String role, ConstraintValidatorContext constraintValidatorContext) {
        List<String> list = Arrays.asList(ROLE_1,ROLE_2,ROLE_3);
        return list.contains(role);
    }
}
