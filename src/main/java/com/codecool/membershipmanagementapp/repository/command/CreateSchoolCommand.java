/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.command;

import com.codecool.membershipmanagementapp.repository.dto.AddressDto;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CreateSchoolCommand {

    @NotBlank(message = "Id is mandatory")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Two capital letters is mandatory.")
    @Schema(description = "Id of school", example = "AJ")
    private String id;

    @NotNull(message = "Group id is mandatory")
    @Min(value = 1, message = "Group id must be between 1 and 9.")
    @Max(value = 9, message = "Group id must be between 1 and 9.")
    @Schema(description = "Group id of school", example = "9")
    private Short groupId;

    @NotBlank(message = "Name is mandatory")
    @Schema(description = "Name of school", example = "Arany János Gimnázium")
    private String name;

    @Valid
    @Schema(description = "Address of school")
    private AddressDto address;

    @NotNull(message = "Status is mandatory")
    @Schema(description = "Active or inactive flag", example = "true")
    private Boolean isActive;
}
