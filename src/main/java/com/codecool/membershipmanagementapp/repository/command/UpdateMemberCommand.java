/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.command;

import com.codecool.membershipmanagementapp.model.member.MembershipStatus;
import com.codecool.membershipmanagementapp.model.member.MembershipType;
import com.codecool.membershipmanagementapp.repository.dto.AddressDto;
import com.codecool.membershipmanagementapp.repository.dto.PersonNameDto;
import com.codecool.membershipmanagementapp.repository.dto.SchoolClassDto;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateMemberCommand {

    @NotNull(message = "Status is mandatory")
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "status")
    @Schema(description = "Status of member", example = "ACTIVE")
    private MembershipStatus membershipStatus;

    @NotNull(message = "Type is mandatory")
    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "type")
    @Schema(description = "Type of member", example = "REGULAR")
    private MembershipType membershipType;

    @Valid
    @JsonProperty(value = "name")
    @Schema(description = "Name of member")
    private PersonNameDto personName;

    @Schema(description = "Place of birth", example = "Budapest")
    private String placeOfBirth;

    @Past(message = "Invalid date of birth")
    @Schema(description = "Date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @Valid
    @JsonProperty(value = "class")
    @Schema(description = "School class of member", example = "PS2008B")
    private SchoolClassDto schoolClass;

    @Valid
    @Schema(description = "Address of member")
    private AddressDto address;

    @Email(message = "Invalid email")
    @Schema(description = "Email of member", example = "example@example.com")
    private String email;

    @Pattern(regexp = "\\+\\d{0,14}$", message = "Invalid phone number")
    @Schema(description = "Phone number of member", example = "+36301234567")
    private String phoneNumber;
    @Schema(description = "Comment", example = "any comment")
    private String comment;
    @Schema(description = "Allow newsletter flag", example = "true")
    private Boolean isAllowNewsletter;
}
