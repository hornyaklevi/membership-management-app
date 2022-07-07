/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.repository.command.CreateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.dto.SchoolClassDto;
import com.codecool.membershipmanagementapp.service.SchoolClassService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag( name = "Operations on school classes")
public record SchoolClassController(SchoolClassService schoolClassService) {

    @GetMapping("/classes")
    @Operation(summary = "List of all classes of schools",
            description = "List of all classes of schools.")
    public List<SchoolClassDto> findAll() {
        return schoolClassService.findAll();
    }

    @GetMapping("/class/{id}")
    @Operation(summary = "Find school class by id",
            description = "Find school class by id.")
    public SchoolClassDto findById(
            @Parameter(description = "Id of school class", example = "PS2008B")
            @PathVariable("id") String id) {
        return schoolClassService.findById(id.toUpperCase());
    }

    @PostMapping("/class")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a class",
            description = "Create a class.")
    public SchoolClassDto save(@Valid @RequestBody CreateSchoolClassCommand command) {
        return schoolClassService.save(command);
    }

    @DeleteMapping("/class/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete school class by id",
            description = "Delete school class by id.")
    public void deleteById(
            @Parameter(description = "Id of school class", example = "SI1984D")
            @PathVariable("id") String id) {
        schoolClassService.deleteById(id.toUpperCase());
    }

    @PutMapping("/class/{id}")
    @Operation(summary = "Update school class by id",
            description = "Update school class by id.")
    public SchoolClassDto update(
            @Parameter(description = "Id of school class", example = "DF1982B")
            @PathVariable("id") String id, @Valid @RequestBody UpdateSchoolClassCommand command) {
        return schoolClassService.update(id.toUpperCase(), command);
    }
}
