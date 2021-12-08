package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.mastery.java.task.constants.Constant.DATE_OF_BIRTH;
import static com.mastery.java.task.constants.Constant.DEPARTMENT_ID;
import static com.mastery.java.task.constants.Constant.FIRST_NAME;
import static com.mastery.java.task.constants.Constant.GENDER;
import static com.mastery.java.task.constants.Constant.JOB_TITLE;
import static com.mastery.java.task.constants.Constant.LAST_NAME;

@RestController
public class EmployeeController {
    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping("/employees/all")
    @ResponseStatus(HttpStatus.FOUND)
    public List<Employee> getEmployees() {
        Iterable<Employee> employees = employeeService.getEmployeeDao().findAll();
        return new ArrayList<>((Collection<Employee>) employees);
    }

    @GetMapping("/employees/{ID}")
    @ResponseStatus(HttpStatus.FOUND)
    public Employee getEmployee(@PathVariable Integer ID) {
        return employeeService.getEmployeeDao().findEmployeeByID(ID);
    }

    @GetMapping("/employees/findByName")
    public List<Employee> getEmployeeByFirstNameAndLastName(@RequestParam(name = FIRST_NAME, required = false) String firstName,
                                                            @RequestParam(name = LAST_NAME, required = false) String lastName) {
        return employeeService.getEmployeeDao()
                .findEmployeesByFirstNameAndLastName(firstName.toUpperCase(), lastName.toUpperCase());
    }

    @PutMapping("/employees/{ID}/update")
    @ResponseStatus(HttpStatus.CREATED)
    public Employee update(@PathVariable Integer ID,
                           @RequestParam(name = FIRST_NAME, required = false) String firstName,
                           @RequestParam(name = LAST_NAME, required = false) String lastName,
                           @RequestParam(name = DEPARTMENT_ID, required = false) Integer departmentId,
                           @RequestParam(name = JOB_TITLE, required = false) String jobTitle,
                           @RequestParam(name = GENDER, required = false) Integer gender,
                           @RequestParam(name = DATE_OF_BIRTH, required = false) String dateOfBirth) {

        Employee updatedEmployee = employeeService.updateEmployee(ID, firstName, lastName, departmentId, jobTitle, gender, dateOfBirth);
        return employeeService.getEmployeeDao().save(updatedEmployee);
    }

    @PostMapping("/employees/post")
    public Employee post(@RequestParam(name = FIRST_NAME, required = false) String firstName,
                         @RequestParam(name = LAST_NAME, required = false) String lastName,
                         @RequestParam(name = DEPARTMENT_ID, required = false) Integer departmentId,
                         @RequestParam(name = JOB_TITLE, required = false) String jobTitle,
                         @RequestParam(name = GENDER, required = false) Integer gender,
                         @RequestParam(name = DATE_OF_BIRTH, required = false) String dateOfBirth) {
        return employeeService.putEmployee(firstName, lastName, departmentId, jobTitle, gender, dateOfBirth);
    }

    @PostMapping("/employees/{ID}/delete")
    public Employee delete(@PathVariable Integer ID) {
        Employee employee = employeeService.getEmployeeDao().findEmployeeByID(ID);
        employeeService.getEmployeeDao().delete(employee);
        return employee;
    }
}