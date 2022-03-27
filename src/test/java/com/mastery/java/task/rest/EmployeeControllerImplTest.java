package com.mastery.java.task.rest;

import com.mastery.java.task.dto.Employee;
import com.mastery.java.task.service.EmployeeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeControllerImplTest {

    @Mock
    private EmployeeService employeeService;

    @InjectMocks
    private EmployeeControllerImpl employeeControllerImpl;

    private final List<Employee> employeesForTest;

    {
        employeesForTest = getEmployeesForTest();
    }

    @Test
    public void contextLoads() {
        Assertions.assertNotNull(employeeControllerImpl);
    }

    @Test
    void getEmployeeByID() {
        //given
        when(employeeService.findEmployeeById(anyInt())).thenReturn(Optional.ofNullable(employeesForTest.get(0)));

        //when
        Optional<Employee> resultEmployee = employeeControllerImpl.getEmployeeByID(anyInt());

        //then
        verify(employeeService, times(1)).findEmployeeById(anyInt());
        Assertions.assertNotNull(resultEmployee);
        Assertions.assertEquals(Optional.of(employeesForTest.get(0)), resultEmployee);
    }

    @Test
    void getEmployeeByFirstNameAndLastName() {
        //given
        List<Employee> employeesForMethod = employeesForTest.stream()
                .filter(employee -> employee.getFirstName().equals("SARA") && employee.getLastName().equals("CONOR"))
                .collect(Collectors.toList());
        when(employeeService.findEmployeeByFirstNameAndLastName(anyString(), anyString())).thenReturn(employeesForMethod);

        //when
        List<Employee> resultEmployeeList = employeeControllerImpl.getEmployeeByFirstNameAndLastName(anyString(), anyString());

        //then
        verify(employeeService, times(1)).findEmployeeByFirstNameAndLastName(anyString(), anyString());
        Assertions.assertNotNull(resultEmployeeList);
        Assertions.assertEquals(employeesForMethod, resultEmployeeList);
    }

    @Test
    void updateEmployee() {
        //given
        when(employeeService.updateEmployee(
                employeesForTest.stream()
                        .filter(employee -> employee.getID().equals(1))
                        .peek(employee -> {
                            employee.setFirstName("TEST");
                            employee.setLastName("TESTEROVICH");
                            employee.setDateOfBirth(LocalDate.of(1990, 1, 4));
                        })
                        .collect(Collectors.toList()).get(0))
        ).thenReturn(Optional.ofNullable(employeesForTest.get(0)));

        //when
        Optional<Employee> resultEmployee = employeeControllerImpl.updateEmployee(employeesForTest.get(0));

        //then
        verify(employeeService, times(1)).updateEmployee(employeesForTest.get(0));
        Assertions.assertNotNull(resultEmployee);
        Assertions.assertEquals(Optional.of(employeesForTest.get(0)), resultEmployee);
    }

    @Test
    void postEmployee() {
        //given
        when(employeeService.postEmployee(any(Employee.class))).thenReturn(Optional.ofNullable(employeesForTest.get(5)));

        //when
        Optional<Employee> resultEmployee = employeeControllerImpl.postEmployee(employeesForTest.get(5));

        //then
        verify(employeeService, times(1)).postEmployee(any(Employee.class));
        Assertions.assertNotNull(resultEmployee);
        Assertions.assertEquals(Optional.of(employeesForTest.get(5)), resultEmployee);
    }

    @Test
    void deleteEmployee() {
        //given
        doNothing().when(employeeService).deleteEmployeeById(anyInt());

        //when
        employeeControllerImpl.deleteEmployee(anyInt());

        //then
        verify(employeeService, times(1)).deleteEmployeeById(anyInt());
    }

    private List<Employee> getEmployeesForTest() {
        return Employee.Parser.parseJsonToList(Paths.get("src/test/resources/employee/employees.json").toFile());
    }
}