/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.util.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
@Constraint(validatedBy = YearOfGraduationValidator.class)
public @interface MaxYearOfGraduation {

    String message() default "Year of graduation must be less than current year + 6";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
