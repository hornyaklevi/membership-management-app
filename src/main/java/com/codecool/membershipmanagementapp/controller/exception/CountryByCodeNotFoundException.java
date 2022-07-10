/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class CountryByCodeNotFoundException extends AbstractThrowableProblem {

    public CountryByCodeNotFoundException(String code) {
        super(URI.create("countries/country-not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Country with code %s not found.", code));
    }
}
