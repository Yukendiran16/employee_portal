package com.ideas2it.util;

import org.apache.commons.validator.routines.EmailValidator;
import org.apache.commons.validator.routines.DateValidator;
import org.apache.commons.validator.routines.RegexValidator;

import java.time.LocalDate;
import java.util.Scanner;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ideas2it.exception.EmailMismatchException;

public class EmployeeUtil {


    private static int sequence;    

    /**
     * method used to create employeeId 
     * @return {@link String} returns employeeId
     */           
     public static String createAndGetEmployeeId() {         

        String createId = "I2I";
        LocalDate localDate = LocalDate.now();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();  
        createId = createId + year%100 + month + sequence;
        sequence = sequence + 1;
        return createId; 
    }

    /**
     * method used to validation of inputs 
     * @param {@link String} details
     * @return {@link boolean} returns boolean
     */
    public boolean matchRegex(String pattern, String input) {

        return new RegexValidator(pattern).isValid(input);
    }

    /**
     * method used to validate date of birth 
     * @param {@link String} employeeDateOfBirth
     * @return {@link boolean} returns boolean
     */
    public static boolean validationOfDateOfBirth(String employeeDateOfBirth) {
    
        return DateValidator.getInstance().isValid(employeeDateOfBirth);
    }

    /**
     * method used to validate mail 
     * @param {@link String} mailId
     * @return {@link boolean} returns boolean
     */
    public static boolean validationOfMail(String identifier) throws EmailMismatchException{
        
        boolean mail = EmailValidator.getInstance().isValid(identifier);
        if (mail == false) {
            throw new EmailMismatchException("invalid email");
        } 
        return mail;
    }
}






