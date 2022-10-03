package com.ideas2it.exception;

/**
 * <h2>EmailMismatchException</h2>
 * <p>
 * The EmailMismatchException class is extends Exception
 * the class is created for custom exception handling for email validation
 * and return data's to EmployeeUtil
 * </p>
 *
 * @author Yukendiran K
 * @version 1.0
 * @since 2022-08-04
 */
public class EmailMismatchException extends Exception {

    public EmailMismatchException(String email) {
        super(email);
    }
}