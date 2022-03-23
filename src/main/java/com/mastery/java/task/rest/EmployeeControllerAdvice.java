package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Response;
import com.mastery.java.task.exceptions.EmployeeException;
import com.mastery.java.task.exceptions.EmployeeValidationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class EmployeeControllerAdvice {

    @ResponseBody
    @ExceptionHandler(EmployeeException.class)
    public ResponseEntity<Response> employeeNotFoundsException(EmployeeException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ResponseBody
    @ExceptionHandler(EmployeeValidationException.class)
    public ResponseEntity<Response> employeeValidationException(EmployeeValidationException ex) {
        Response response = new Response(ex.getMessage());
        return new ResponseEntity<>(response, HttpStatus.METHOD_NOT_ALLOWED);
    }
}