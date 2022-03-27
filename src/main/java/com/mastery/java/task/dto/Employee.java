package com.mastery.java.task.dto;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.mastery.java.task.annotations.Adult;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Entity
public class Employee {
    @Id
    @NotNull
    private Integer ID;

    private String firstName;
    private String lastName;

    @NotNull
    private Integer departmentId;

    private String jobTitle;

    @Enumerated(value = EnumType.STRING)
    private Gender gender;

    @Adult
    private LocalDate dateOfBirth;

    public Employee() {
    }

    public Employee(Integer ID,
                    String firstName,
                    String lastName,
                    Integer departmentId,
                    String jobTitle,
                    Gender gender,
                    LocalDate dateOfBirth) {
        this.ID = ID;
        this.firstName = firstName;
        this.lastName = lastName;
        this.departmentId = departmentId;
        this.jobTitle = jobTitle;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer employeeId) {
        this.ID = employeeId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Integer getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(Integer departmentId) {
        this.departmentId = departmentId;
    }

    public String getJobTitle() {
        return jobTitle;
    }

    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public LocalDate getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(LocalDate dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(firstName, employee.firstName) && Objects.equals(lastName, employee.lastName) &&
                gender == employee.gender && Objects.equals(dateOfBirth, employee.dateOfBirth);
    }

    @Override
    public int hashCode() {
        return Objects.hash(firstName, lastName, gender, dateOfBirth);
    }

    @Override
    public String toString() {
        return "Employee{" +
                "employeeId=" + ID +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", departmentId=" + departmentId +
                ", jobTitle='" + jobTitle + '\'' +
                ", gender=" + gender +
                ", dateOfBirth=" + dateOfBirth +
                '}';
    }

    public static class Parser {
        private static final ObjectMapper mapper = new ObjectMapper();

        static {
            mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
            mapper.registerModule(new JavaTimeModule());
            mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
            mapper.configure(DeserializationFeature.FAIL_ON_MISSING_CREATOR_PROPERTIES, false);
        }

        public static String toJson(Employee user) {
            try {
                final StringWriter writer = new StringWriter();
                mapper.writeValue(writer, user);
                return writer.toString();
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        public static Employee parseJson(String json) {
            try {
                return mapper.readValue(json, Employee.class);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        public static Employee parseJson(File file) {
            try {
                return mapper.readValue(file, Employee.class);
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }

        public static List<Employee> parseJsonToList(File file) {
            try {
                return mapper.readValue(file, new TypeReference<List<Employee>>() {});
            } catch (IOException exc) {
                throw new RuntimeException(exc);
            }
        }
    }
}