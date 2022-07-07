/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.command;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCountryCommand {

    @NotBlank(message = "Hungarian name is mandatory")
    @Pattern(regexp = "^[A-ZÁÉÚŐÓÜÖÍ].*", message = "Capital initials mandatory")
    @Schema(description = "Hungarian name of the country", example = "Tesztország")
    private String nameOfCountryHun;

    @NotBlank(message = "English name is mandatory")
    @Pattern(regexp = "^[A-Z].*", message = "Capital initials mandatory")
    @Schema(description = "English name of the country", example = "Testland")
    private String nameOfCountryEng;

}
