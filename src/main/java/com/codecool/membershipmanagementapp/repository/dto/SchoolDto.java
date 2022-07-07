/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SchoolDto implements Serializable {

    @NotBlank(message = "Id is mandatory")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Two capital letters is mandatory.")
    @Schema(description = "Id of school", example = "DF")
    private String id;

    @Schema(description = "Group id of school", example = "7")
    private Short groupId;
    @Schema(description = "Name of school", example = "Deák Ferenc Gimnázium")
    private String name;
    @Schema(description = "Address of school")
    private AddressDto address;
    @Schema(description = "Active or inactive flag", example = "true")
    private Boolean isActive;

    @JsonProperty(value = "classes")
    @JsonIgnoreProperties(value = {"school"})
    @Schema(description = "Classes of school")
    private List<SchoolClassDto> schoolClasses;
}
