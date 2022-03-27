package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Response;
import com.mastery.java.task.exceptions.EmployeeException;
import com.mastery.java.task.exceptions.EmployeeValidationException;
import org.springframework.http.ResponseEntity;

public interface EmployeeControllerAdvice {
    ResponseEntity<Response> employeeNotFoundsException(EmployeeException ex);

    ResponseEntity<Response> employeeValidationException(EmployeeValidationException ex);
}