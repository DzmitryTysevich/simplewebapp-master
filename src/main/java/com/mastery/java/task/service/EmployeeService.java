package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.exceptions.EmployeeHasBeenFoundException;
import com.mastery.java.task.exceptions.EmployeeNotFoundsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static com.mastery.java.task.constants.Constant.FOUND;
import static com.mastery.java.task.constants.Constant.NOT_FOUND;

@Service
public class EmployeeService {
    private static final Logger logger = LoggerFactory.getLogger(EmployeeService.class);
    private final EmployeeDao employeeDao;

    @Autowired
    public EmployeeService(EmployeeDao employeeDao) {
        this.employeeDao = employeeDao;
    }

    public List<Employee> findAllEmployee() {
        Iterable<Employee> employees = employeeDao.findAll();
        logger.info("Searching for employees...");
        return new ArrayList<>((Collection<Employee>) employees);
    }

    public Optional<Employee> findEmployeeById(Integer ID) {
        Optional<Employee> employee = employeeDao.findById(ID);
        if (employeeDao.existsById(ID)) {
            logger.info("Searching for employee by ID...");
            return employee;
        } else {
            throw new EmployeeNotFoundsException(NOT_FOUND);
        }
    }

    public List<Employee> findEmployeeByFirstNameAndLastName(String firstName, String lastName) {
        if (firstName != null && lastName != null) {
            logger.info("Searching for employee by names...");
            List<Employee> employeeList = employeeDao.findEmployeesByFirstNameAndLastName(firstName.toUpperCase(), lastName.toUpperCase());
            if (employeeList.isEmpty()) {
                throw new EmployeeNotFoundsException(NOT_FOUND);
            } else return employeeList;
        } else {
            logger.warn("Enter first name and last name");
            return findAllEmployee();
        }
    }

    public void deleteEmployeeById(Integer ID) {
        if (employeeDao.existsById(ID)) {
            employeeDao.deleteById(ID);
            logger.info("Employee was deleted");
        } else {
            throw new EmployeeNotFoundsException(NOT_FOUND);
        }
    }

    public Optional<Employee> updateEmployee(Employee employee) {
        if (employeeDao.existsById(employee.getID())) {
            logger.info("Employee was updated");
            return Optional.of(employeeDao.save(new Employee(
                    employee.getID(),
                    employee.getFirstName().toUpperCase(),
                    employee.getLastName().toUpperCase(),
                    employee.getDepartmentId(),
                    employee.getJobTitle().toUpperCase(),
                    employee.getGender(),
                    employee.getDateOfBirth()
            )));
        } else {
            throw new EmployeeNotFoundsException(NOT_FOUND);
        }
    }

    public Optional<Employee> postEmployee(Employee employee) {
        if (!isaEmployeeExistsByNamesAndDateOfBirth(employee) && !employeeDao.existsById(employee.getID())) {
            logger.info("Employee was added");
            return Optional.of(employeeDao.save(new Employee(
                    employee.getID(),
                    employee.getFirstName().toUpperCase(),
                    employee.getLastName().toUpperCase(),
                    employee.getDepartmentId(),
                    employee.getJobTitle().toUpperCase(),
                    employee.getGender(),
                    employee.getDateOfBirth()
            )));
        } else {
            throw new EmployeeHasBeenFoundException(FOUND);
        }
    }

    private boolean isaEmployeeExistsByNamesAndDateOfBirth(Employee employee) {
        return employeeDao.existsEmployeeByFirstNameAndLastNameAndDateOfBirth(
                employee.getFirstName().toUpperCase(),
                employee.getLastName().toUpperCase(),
                employee.getDateOfBirth()
        );
    }
}