package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeController {
    Optional<Employee> getEmployeeByID(Integer ID);

    List<Employee> getEmployeeByFirstNameAndLastName(String firstName, String lastName);

    Optional<Employee> updateEmployee(Employee employee);

    Optional<Employee> postEmployee(Employee employee);

    void deleteEmployee(Integer ID);
}