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
import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonNameDto implements Serializable {

    @Schema(description = "Prefix of name", example = "dr.")
    private String prefixOfName;

    @NotBlank(message = "Last name is mandatory")
    @Schema(description = "Last name", example = "Tóth")
    private String lastName;

    @NotBlank(message = "First name is mandatory")
    @Schema(description = "First name", example = "István")
    private String firstName;

    @Schema(description = "Suffix of name", example = "PhD")
    private String suffixOfName;
    @Schema(description = "Nickname", example = "Pisti")
    private String nickName;
}
