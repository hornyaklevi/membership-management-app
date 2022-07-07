/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.dto;

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
public class CountryDto implements Serializable {

    @NotBlank(message = "Code is mandatory")
    @Pattern(regexp = "^[A-Z]{2}$", message = "Two capital letters is mandatory.")
    @Schema(description = "Code of the country (ISO-3166)", example = "HU")
    private String code;
    @Schema(description = "Hungarian name of the country", example = "Magyarország")
    private String nameOfCountryHun;
    @Schema(description = "English name of the country", example = "Hungary")
    private String nameOfCountryEng;
}