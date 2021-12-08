package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.utils.EmployeeUtil;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import static com.mastery.java.task.constants.Constant.*;

@Service
public class EmployeeService {
    private final EmployeeDao employeeDao;
    private final EmployeeUtil employeeUtil;

    public EmployeeService(EmployeeDao employeeDao, EmployeeUtil employeeUtil) {
        this.employeeDao = employeeDao;
        this.employeeUtil = employeeUtil;
    }

    public EmployeeDao getEmployeeDao() {
        return employeeDao;
    }

    public Employee putEmployee(String firstName, String lastName, Integer departmentId, String jobTitle, Integer gender, String dateOfBirth) {
        Employee employee;
        if (!isaEmployeeExists(firstName, lastName, dateOfBirth)) {
            employee = new Employee(
                    firstName.toUpperCase(),
                    lastName.toUpperCase(),
                    departmentId,
                    jobTitle.toUpperCase(),
                    employeeUtil.getGenderFromIndex(gender),
                    employeeUtil.getDateOfBirthFromString(dateOfBirth));
            employeeDao.save(employee);
        } else {
            throw new ResponseStatusException(HttpStatus.CONFLICT, firstName + SPACE + lastName + CREATED_YES);
        }
        return employee;
    }

    public Employee updateEmployee(Integer ID, String firstName, String lastName, Integer departmentId,
                                   String jobTitle, Integer gender, String dateOfBirth) {
        Employee updatedEmployee;
        if (employeeDao.existsById(ID)) {
            updatedEmployee = employeeDao.findEmployeeByID(ID);
        } else {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, CREATED_NO);
        }

        if (isNotNull(firstName, updatedEmployee)) {
            updatedEmployee.setFirstName(firstName.toUpperCase());
        }
        if (isNotNull(lastName, updatedEmployee)) {
            updatedEmployee.setLastName(lastName.toUpperCase());
        }
        if (isNotNull(departmentId, updatedEmployee)) {
            updatedEmployee.setDepartmentId(departmentId);
        }
        if (isNotNull(jobTitle, updatedEmployee)) {
            updatedEmployee.setJobTitle(jobTitle.toUpperCase());
        }
        if (isNotNull(gender, updatedEmployee)) {
            updatedEmployee.setGender(employeeUtil.getGenderFromIndex(gender));
        }
        if (isNotNull(dateOfBirth, updatedEmployee)) {
            updatedEmployee.setDateOfBirth(employeeUtil.getDateOfBirthFromString(dateOfBirth));
        }
        return updatedEmployee;
    }

    private boolean isaEmployeeExists(String firstName, String lastName, String dateOfBirth) {
        return employeeDao.existsEmployeeByFirstNameAndLastNameAndDateOfBirth(
                firstName.toUpperCase(),
                lastName.toUpperCase(),
                employeeUtil.getDateOfBirthFromString(dateOfBirth)
        );
    }

    private boolean isNotNull(Object object, Employee updatedEmployee) {
        return object != null && updatedEmployee != null;
    }
}