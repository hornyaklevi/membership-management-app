/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository.dto;

import com.codecool.membershipmanagementapp.model.member.MembershipStatus;
import com.codecool.membershipmanagementapp.model.member.MembershipType;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@JsonPropertyOrder({"memberId", "name", "class", "placeOfBirth", "dateOfBirth", "address", "email", "phoneNumber",
        "status", "type", "comment", "createdAt", "updatedAt"})
public class MemberDto implements Serializable {

    @Schema(description = "Id of member", example = "1")
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "status")
    @Schema(description = "Status of member", example = "ACTIVE")
    private MembershipStatus membershipStatus;

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "type")
    @Schema(description = "Type of member", example = "REGULAR")
    private MembershipType membershipType;

    @JsonProperty(value = "name")
    @Schema(description = "Name of member")
    private PersonNameDto personName;

    @Schema(description = "Place of birth", example = "Budapest")
    private String placeOfBirth;
    @Schema(description = "Date of birth", example = "1990-01-01")
    private LocalDate dateOfBirth;

    @JsonProperty(value = "class")
    @Schema(description = "School class of member", example = "PS2008B")
    private SchoolClassDto schoolClass;

    @Schema(description = "Address of member")
    private AddressDto address;
    @Schema(description = "Email of member", example = "example@example.com")
    private String email;
    @Schema(description = "Phone number of member", example = "+36301234567")
    private String phoneNumber;
    @Schema(description = "Comment", example = "any comment")
    private String comment;
    @Schema(description = "Allow newsletter flag", example = "true")
    private Boolean isAllowNewsletter;
    @Schema(description = "Created At", example = "2022-06-28 15:10:25")
    private LocalDateTime createdAt;
    @Schema(description = "Updated At", example = "2022-06-29 08:15:32")
    private LocalDateTime updatedAt;
}
