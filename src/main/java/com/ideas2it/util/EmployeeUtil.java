package com.ideas2it.util;

import com.ideas2it.exception.EmailMismatchException;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;

import java.time.LocalDate;

/**
 * <h2>EmployeeUtil</h2>
 * <p>
 * The class implements an application that
 * defines create trainer id, trainee id and
 * all user input validations will be performed
 * and return validated input's to controller.
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
public class EmployeeUtil {

    /**
     * <h1> matchRegex </h1>
     * <p>
     * method used to validation of inputs
     * </p>
     *
     * @param pattern ss
     * @return {@link boolean} returns boolean
     */
    public boolean matchRegex(String pattern, String input) {

        return new RegexValidator(pattern).isValid(input);
    }

    /**
     * <h1> validationOfDateOfBirth </h1>
     * <p>
     * method used to validate date of birth
     *
     * @param employeeDateOfBirth s
     * @return {@link boolean} returns boolean
     */
    public static boolean validationOfDateOfBirth(String employeeDateOfBirth)
            throws NumberFormatException, ArrayIndexOutOfBoundsException {
        boolean bool;
        LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        String[] date = employeeDateOfBirth.split("-");
        int year = Integer.parseInt(date[0]);

        if ((currentYear - 60) <= year && year <= (currentYear - 18)) {
            bool = DateValidator.getInstance().isValid(employeeDateOfBirth, "yyyy-MM-dd");
        } else {
            bool = false;
        }
        return bool;
    }

    /**
     * <h1> validationOfMail </h1>
     * <p>
     * method used to validate the mail
     * </p>
     * @param identifier is an input mail id
     * @return boolean
     */
    public static boolean validationOfMail(String identifier)
            throws EmailMismatchException {
        return EmailValidator.getInstance().isValid(identifier);
    }
}






