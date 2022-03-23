package com.mastery.java.task.exceptions;

public class EmployeeValidationException extends RuntimeException{
    public EmployeeValidationException(String message) {
        super(message);
    }
}