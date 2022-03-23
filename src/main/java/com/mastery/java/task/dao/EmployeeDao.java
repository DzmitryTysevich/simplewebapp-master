package com.mastery.java.task.dao;

import com.mastery.java.task.dto.Employee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface EmployeeDao extends CrudRepository<Employee, Integer> {

    List<Employee> findEmployeesByFirstNameAndLastName(String firstName, String lastName);

    boolean existsEmployeeByFirstNameAndLastNameAndDateOfBirth(String firstName, String lastName, LocalDate dateOfBirth);
}