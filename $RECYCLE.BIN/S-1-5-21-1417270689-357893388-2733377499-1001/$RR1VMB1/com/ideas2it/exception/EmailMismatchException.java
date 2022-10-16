package com.ideas2it.exception;

public class EmailMismatchException extends Exception {

    public EmailMismatchException(String email) {
        super(email);
    }
}