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

import javax.validation.Valid;
import javax.validation.constraints.Pattern;
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddressDto implements Serializable {

    @Valid
    @JsonIgnoreProperties(value = {"nameOfCountryEng"})
    @Schema(description = "Country")
    private CountryDto country;

    @Pattern(regexp = "(?i)^[a-z0-9][a-z0-9\\- ]{0,10}[a-z0-9]$", message = "Invalid postal code")
    @Schema(description = "Postal code", example = "2700")
    private String zipCode;
    @Schema(description = "City", example = "Cegléd")
    private String city;
    @Schema(description = "State  or province", example = "Pest megye")
    private String stateOrProvince;
    @Schema(description = "Street", example = "Kossuth Lajos utca 1.")
    private String street;

    @Schema(description = "Full address", example = "HU 2700 Cegléd, Kossuth Lajos utca 1.")
    public String getPostalAddress() {

        if ((country == null) || (country.getCode() == null) || (zipCode == null) || (city == null) || (street == null) ||
                country.getCode().isEmpty() || zipCode.isEmpty() || city.isEmpty() || street.isEmpty()) return "";

        return String.join(" ",
                country.getCode(),
                zipCode,
                city + ",",
                street);
    }
}
