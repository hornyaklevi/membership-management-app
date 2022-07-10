/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class MemberByIdNotFoundException extends AbstractThrowableProblem {

    public MemberByIdNotFoundException(Long id) {
        super(URI.create("members/member-not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Member with id %d not found.", id));
    }
}
