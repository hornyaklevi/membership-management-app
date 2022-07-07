/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.repository.command.CreateSchoolCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateSchoolCommand;
import com.codecool.membershipmanagementapp.repository.dto.SchoolDto;
import com.codecool.membershipmanagementapp.service.SchoolService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag( name = "Operations on schools")
public record SchoolController(SchoolService schoolService) {

    @GetMapping("/schools")
    @Operation(summary = "List of all schools",
            description = "List of all schools.")
    public List<SchoolDto> findAll() {
        return schoolService.findAll();
    }

    @GetMapping("/school/{id}")
    @Operation(summary = "Find school by id",
            description = "Find school by id.")
    public SchoolDto findById(
            @Parameter(description = "Id of school", example = "PS")
            @PathVariable("id") String id) {
        return schoolService.findById(id.toUpperCase());
    }

    @PostMapping("/school")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a school",
            description = "Create a school.")
    public SchoolDto save(@Valid @RequestBody CreateSchoolCommand command) {
        return schoolService.save(command);
    }

    @DeleteMapping("/school/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete school by id",
            description = "Delete school by id.")
    public void deleteById(
            @Parameter(description = "Id of school", example = "DF")
            @PathVariable("id") String id) {
        schoolService.deleteById(id.toUpperCase());
    }

    @PutMapping("/school/{id}")
    @Operation(summary = "Update school by id",
            description = "Update school by id.")
    public SchoolDto update(
            @Parameter(description = "Id of school", example = "DF")
            @PathVariable("id") String id, @Valid @RequestBody UpdateSchoolCommand command) {
        return schoolService.update(id.toUpperCase(), command);
    }
}
