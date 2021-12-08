package com.mastery.java.task.utils;

import com.mastery.java.task.dto.Gender;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.Arrays;

@Component
public class EmployeeUtil {
    public LocalDate getDateOfBirthFromString(String dateOfBirth) {
        return LocalDate.parse(dateOfBirth.toUpperCase());
    }

    public Gender getGenderFromIndex(Integer gender) {
        return Arrays.asList(Gender.values()).get(gender);
    }
}