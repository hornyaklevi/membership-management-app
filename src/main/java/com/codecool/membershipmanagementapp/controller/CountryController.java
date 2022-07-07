/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.repository.command.CreateCountryCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateCountryCommand;
import com.codecool.membershipmanagementapp.repository.dto.CountryDto;
import com.codecool.membershipmanagementapp.service.CountryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag( name = "Operations on countries")
public record CountryController(CountryService countryService) {

    @GetMapping("/countries")
    @Operation(summary = "List of all countries",
            description = "List of all countries.")
    public List<CountryDto> findAll() {
        return countryService.findAll();
    }

    @GetMapping("/country/{code}")
    @Operation(summary = "Find country by ISO-3166 country code",
            description = "Find country by ISO-3166 country code.")
    public CountryDto findByCode(
            @Parameter(description = "Code of the country", example = "HU")
            @PathVariable("code") String code) {
        return countryService.findByCode(code.toUpperCase());
    }

    @GetMapping("/country")
    @Operation(summary = "Find country by hungarian name",
            description = "Find country by hungarian name.")
    public CountryDto findCountryByNameHun(
            @Parameter(description = "Hungarian name of the country", example = "Spanyolország")
            @RequestParam("name_hu") String name) {
        return countryService.findCountryByNameHun(name);
    }

    @PostMapping("/country")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a country",
            description = "Create a country.")
    public CountryDto save(@Valid @RequestBody CreateCountryCommand command) {
        return countryService.save(command);
    }

    @DeleteMapping("/country/{code}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete country by code",
            description = "Delete country by code.")
    public void deleteById(
            @Parameter(description = "Code of the country", example = "ZW")
            @PathVariable("code") String code) {
        countryService.deleteById(code.toUpperCase());
    }

    @PutMapping("/country/{code}")
    @Operation(summary = "Update country by code",
            description = "Update country by code.")
    public CountryDto update(
            @Parameter(description = "Code of the country", example = "AQ")
            @PathVariable("code") String code, @Valid @RequestBody UpdateCountryCommand command) {
        return countryService.update(code.toUpperCase(), command);
    }
}
