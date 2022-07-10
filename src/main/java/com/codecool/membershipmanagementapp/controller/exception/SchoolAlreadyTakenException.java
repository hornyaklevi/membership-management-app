/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SchoolAlreadyTakenException extends AbstractThrowableProblem {

    public SchoolAlreadyTakenException(String id) {
        super(URI.create("schools/school-already-taken"),
                "Already taken",
                Status.BAD_REQUEST,
                String.format("School with id %s already taken.", id));
    }
}
