package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.mastery.java.task.constants.Constant.FIRST_NAME;
import static com.mastery.java.task.constants.Constant.LAST_NAME;

@RestController
@RequestMapping("/employees")
public class EmployeeController {
    private final EmployeeService employeeService;

    @Autowired
    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @GetMapping(value = "/{ID}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Employee getEmployeeByID(@PathVariable Integer ID) {
        return employeeService.findEmployeeById(ID);
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Employee> getEmployeeByFirstNameAndLastName(@RequestParam(name = FIRST_NAME, required = false) String firstName,
                                                            @RequestParam(name = LAST_NAME, required = false) String lastName) {
        return employeeService.findEmployeeByFirstNameAndLastName(firstName, lastName);
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee updateEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.updateEmployee(employee);
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public Employee postEmployee(@Valid @RequestBody Employee employee) {
        return employeeService.postEmployee(employee);
    }

    @PostMapping(value = "/{ID}")
    public void deleteEmployee(@PathVariable Integer ID) {
        employeeService.deleteEmployee(ID);
    }
}