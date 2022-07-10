/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SchoolClassByIdNotFoundException extends AbstractThrowableProblem {

    public SchoolClassByIdNotFoundException(String id) {
        super(URI.create("classes/class-not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("School class with id %s not found.", id));
    }
}
