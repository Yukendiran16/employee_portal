package com.ideas2it.util;

import java.sql.SQLException;
import java.time.LocalDate;

import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.RegexValidator;

import com.ideas2it.exception.EmailMismatchException;
 
/**
 *
 * <h2>EmployeeUtil</h2>
 * 
 * The class implements an application that
 * defines create trainer id, trainee id and 
 * all user input validations will be performed
 * and return validated input's to controller. 
 *
 * @author  Yukendiran K
 * @version 1.0
 * @since   2022-08-04 
 *
 */
public class EmployeeUtil {


    private static int sequence = 0;    

    /**
     * <h1> createAndGetTrainerId </h1>
     *
     * method used to create and get trainer Id 
     *
     * @param {@link int} trainer id
     * @return {@link String} returns employeeId
     *
     */           
     public static String createAndGetTrainerId(int trainerId) throws SQLException {         

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();  
        String createId = "I2I" + year%100 + "TR" + sequence;
        sequence = trainerId + 1;
        return createId; 
    }

    /**
     *
     * <h1> createAndGetTraineeId </h1>
     *
     * method used to create and get trainer Id
     *
     * @param {@link int} trainee id 
     * @return {@link String} returns employeeId
     *
     */           
     public static String createAndGetTraineeId(int traineeId) throws SQLException {         

        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();  
        String createId = "I2I" + year%100 + "TE" + sequence;
        sequence = traineeId + 1;
        return createId; 
    }

    /**
     *
     * <h1> matchRegex </h1>
     *
     * method used to validation of inputs 
     *
     * @param {@link String} details
     * @return {@link boolean} returns boolean
     *
     */
    public boolean matchRegex(String pattern, String input) {
        return new RegexValidator(pattern).isValid(input);
    }

    /**
     *
     * <h1> validationOfDateOfBirth </h1>
     *
     * method used to validate date of birth 
     *
     * @param {@link String} employeeDateOfBirth
     * @return {@link boolean} returns boolean
     *
     */
    public static boolean validationOfDateOfBirth(String employeeDateOfBirth) {
            return DateValidator.getInstance().isValid(employeeDateOfBirth);
    }

    /**
     *
     * <h1> validationOfMail </h1>
     *
     * method used to validate the mail 
     *
     * @param {@link String} mailId
     * @return {@link boolean} returns boolean
     *
     */
    public static boolean validationOfMail(String identifier) throws EmailMismatchException {
        boolean mail = EmailValidator.getInstance().isValid(identifier);

        if (mail == false) {
            throw new EmailMismatchException("invalid email");
        } 
        return mail;
    }
}






