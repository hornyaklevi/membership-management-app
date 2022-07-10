/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller.exception;

import org.zalando.problem.AbstractThrowableProblem;
import org.zalando.problem.Status;

import java.net.URI;

public class CountryByNameNotFoundException extends AbstractThrowableProblem {

    public CountryByNameNotFoundException(String name) {
        super(URI.create("countries/country-not-found"),
                "Not found",
                Status.NOT_FOUND,
                String.format("Country with name %s not found.", name));
    }
}
