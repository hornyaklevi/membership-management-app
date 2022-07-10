/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SchoolByIdNotFoundException extends AbstractThrowableProblem {

    public SchoolByIdNotFoundException(String id) {
        super(URI.create("schools/school-not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("School with id %s not found.", id));
    }
}
