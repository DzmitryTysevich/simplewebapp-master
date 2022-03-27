package com.mastery.java.task.service;

import com.mastery.java.task.dao.EmployeeDao;
import com.mastery.java.task.dto.Employee;
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

import static org.mockito.ArgumentMatchers.intThat;
import static org.mockito.Mockito.any;
import static org.mockito.Mockito.anyInt;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTest {

    @Mock
    private EmployeeDao employeeDao;

    @InjectMocks
    private EmployeeService employeeService;

    private final List<Employee> employeesForTest;

    {
        employeesForTest = getEmployeesForTest();
    }

    @Test
    void shouldFindAllEmployee() {
        // given
        when(employeeDao.findAll()).thenReturn(employeesForTest);

        // when
        List<Employee> resultListEmployee = employeeService.findAllEmployee();

        // then
        verify(employeeDao, times(1)).findAll();
        Assertions.assertNotNull(resultListEmployee);
    }

    @Test
    void shouldFindEmployeeById() {
        //given
        when(employeeDao.existsById(intThat(integer -> integer != 0))).thenReturn(true);
        when(employeeDao.findById(1)).thenReturn(Optional.ofNullable(employeesForTest.get(0)));

        //when
        Optional<Employee> resultEmployee = employeeService.findEmployeeById(1);

        //then
        verify(employeeDao, times(1)).existsById(1);
        verify(employeeDao, times(1)).findById(1);
        Assertions.assertNotNull(resultEmployee);
        Assertions.assertEquals(Optional.of(employeesForTest.get(0)), resultEmployee);
    }

    @Test
    void shouldFindEmployeeByFirstNameAndLastName() {
        //given
        List<Employee> employeesForMethod = employeesForTest.stream()
                .filter(employee -> employee.getFirstName().equals("SARA") && employee.getLastName().equals("CONOR"))
                .collect(Collectors.toList());
        when(employeeDao.findEmployeesByFirstNameAndLastName("SARA", "CONOR")).thenReturn(employeesForMethod);

        //when
        List<Employee> resultEmployeeList = employeeService.findEmployeeByFirstNameAndLastName("SARA", "CONOR");

        //then
        verify(employeeDao, times(1)).findEmployeesByFirstNameAndLastName("SARA", "CONOR");
        Assertions.assertNotNull(resultEmployeeList);
        Assertions.assertEquals(employeesForMethod, resultEmployeeList);
    }

    @Test
    void shouldDeleteEmployee() {
        //given
        when(employeeDao.existsById(intThat(integer -> integer != 0))).thenReturn(true);
        doNothing().when(employeeDao).deleteById(1);

        //when
        employeeService.deleteEmployeeById(1);

        //then
        verify(employeeDao, times(1)).existsById(1);
        verify(employeeDao, times(1)).deleteById(1);
    }

    @Test
    void shouldUpdateEmployee() {
        //given
        when(employeeDao.existsById(intThat(integer -> integer != 0))).thenReturn(true);
        when(employeeDao.save(
                employeesForTest.stream()
                        .filter(employee -> employee.getID().equals(1))
                        .peek(employee -> {
                            employee.setFirstName("TEST");
                            employee.setLastName("TESTEROVICH");
                            employee.setDateOfBirth(LocalDate.of(1990, 1, 4));
                        })
                        .collect(Collectors.toList()).get(0))
        ).thenReturn(employeesForTest.get(0));

        //when
        Optional<Employee> resultEmployee = employeeService.updateEmployee(employeesForTest.get(0));

        //then
        verify(employeeDao, times(1)).existsById(1);
        verify(employeeDao, times(1)).save(employeesForTest.get(0));
        Assertions.assertNotNull(resultEmployee);
        Assertions.assertEquals(Optional.of(employeesForTest.get(0)), resultEmployee);
    }

    @Test
    void postEmployee() {
        //given
        when(employeeDao.existsById(anyInt())).thenReturn(false);
        when(employeeDao.existsEmployeeByFirstNameAndLastNameAndDateOfBirth(anyString(), anyString(), any())).thenReturn(false);
        when(employeeDao.save(any(Employee.class))).thenReturn(employeesForTest.get(5));

        //when
        Optional<Employee> resultEmployee = employeeService.postEmployee(employeesForTest.get(5));

        //then
        verify(employeeDao, times(1)).existsById(anyInt());
        verify(employeeDao, times(1)).existsEmployeeByFirstNameAndLastNameAndDateOfBirth(anyString(), anyString(), any(LocalDate.class));
        verify(employeeDao, times(1)).save(any(Employee.class));
        Assertions.assertNotNull(resultEmployee);
        Assertions.assertEquals(Optional.of(employeesForTest.get(5)), resultEmployee);
    }

    private List<Employee> getEmployeesForTest() {
        return Employee.Parser.parseJsonToList(Paths.get("src/test/resources/employee/employees.json").toFile());
    }
}