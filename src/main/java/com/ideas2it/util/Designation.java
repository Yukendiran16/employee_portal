package com.ideas2it.util;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.ideas2it.util.Constant.ROLE_1;
import static com.ideas2it.util.Constant.ROLE_2;
import static com.ideas2it.util.Constant.ROLE_3;
import static java.lang.annotation.ElementType.ANNOTATION_TYPE;
import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.TYPE_USE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Documented
@Retention(RUNTIME)
@Target({FIELD, ANNOTATION_TYPE, TYPE_USE})
@Constraint(validatedBy = DesignationValidator.class)
public @interface Designation {

    String message() default "{invalid designation : must be" + "\n" + ROLE_1 + "\n" + ROLE_2 +  ROLE_3 + " }";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}

