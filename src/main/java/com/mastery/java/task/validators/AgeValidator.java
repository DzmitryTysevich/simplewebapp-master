package com.mastery.java.task.validators;

import com.mastery.java.task.annotations.Adult;
import com.mastery.java.task.constants.Constant;
import com.mastery.java.task.exceptions.EmployeeValidationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class AgeValidator implements ConstraintValidator<Adult, LocalDate> {
    private static final Logger logger = LoggerFactory.getLogger(AgeValidator.class);

    @Override
    public boolean isValid(LocalDate dateOfBirth, ConstraintValidatorContext constraintValidatorContext) {
        LocalDate adult = dateOfBirth.plusYears(Constant.age);
        logger.info("Adult validation is starting...");
        if (adult.isEqual(LocalDate.now()) || adult.isBefore(LocalDate.now())) {
            logger.info("adultValidation.successful");
            return true;
        } else {
            throw new EmployeeValidationException("adultValidation.invalid");
        }
    }
}