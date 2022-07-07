/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.util.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

public class YearOfGraduationValidator implements ConstraintValidator<MaxYearOfGraduation, Short> {

    private int maxValue;

    @Override
    public boolean isValid(Short value, ConstraintValidatorContext context) {
        return value <= maxValue;
    }

    @Override
    public void initialize(MaxYearOfGraduation constraintAnnotation) {
        maxValue = LocalDate.now().getYear() + 6;
    }
}
