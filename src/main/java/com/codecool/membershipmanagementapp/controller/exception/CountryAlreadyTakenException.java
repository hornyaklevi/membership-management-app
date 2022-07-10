/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class CountryAlreadyTakenException extends AbstractThrowableProblem {

    public CountryAlreadyTakenException(String code) {
        super(URI.create("countries/country-already-taken"),
                "Already taken",
                Status.BAD_REQUEST,
                String.format("Country with code %s already taken.", code));
    }
}
