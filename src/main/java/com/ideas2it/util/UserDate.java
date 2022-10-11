package com.ideas2it.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE, TYPE_USE})
@Constraint(validatedBy = DateValidator.class)
public @interface UserDate {

    String message() default "invalid date : DATE format must be \"yyyy-mm-dd\" and age must be between 18 to 50";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
