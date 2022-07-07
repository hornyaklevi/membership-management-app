/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolClassDto implements Serializable {

    @NotBlank(message = "Class Id is mandatory")
    @Pattern(regexp = "^[A-Z]{2}\\d{4}[A-Z]$", message = "Invalid class id format")
    @Schema(description = "Id of school", example = "PS2008B")
    private String id;

    @Schema(description = "Year of graduation", example = "2008")
    private Short yearOfGraduation;
    @Schema(description = "Mark of class", example = "B")
    private String markOfClass;
    @Schema(description = "Name of form teacher", example = "Tóth Bertalan")
    private String formTeacher;

    @JsonIgnoreProperties(value = {"address", "classes"})
    @Schema(description = "School of the class")
    private SchoolDto school;
}
