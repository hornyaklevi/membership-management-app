/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.command;

import com.codecool.membershipmanagementapp.repository.dto.SchoolDto;
import com.codecool.membershipmanagementapp.util.validation.MaxYearOfGraduation;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateSchoolClassCommand {

    @NotNull(message = "Year of graduation is mandatory")
    @Min(value = 1920, message = "Year of graduation must be greater than 1920")
    @MaxYearOfGraduation
    @Schema(description = "Year of graduation", example = "1982")
    private Short yearOfGraduation;

    @NotBlank(message = "Mark of class is mandatory")
    @Pattern(regexp = "^[A-Z]{1}$", message = "The format of the class mark is invalid: only one capital letter")
    @Schema(description = "Mark of class", example = "B")
    private String markOfClass;

    @Schema(description = "Name of form teacher", example = "Kiss Gábor")
    private String formTeacher;

    @JsonIgnoreProperties(value = {"address", "classes"})
    @Valid
    @NotNull(message = "School id is mandatory")
    @Schema(description = "School of the class")
    private SchoolDto school;
}
