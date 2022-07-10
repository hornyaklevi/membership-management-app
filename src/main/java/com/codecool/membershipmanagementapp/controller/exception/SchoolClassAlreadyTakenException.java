/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class SchoolClassAlreadyTakenException extends AbstractThrowableProblem {

    public SchoolClassAlreadyTakenException(String id) {
        super(URI.create("classes/class-already-taken"),
                "Already taken",
                Status.BAD_REQUEST,
                String.format("Class with id %s already taken.", id));
    }
}
