/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.NotFound;
import org.hibernate.annotations.NotFoundAction;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Address {

    @ManyToOne
    @JoinColumn(name = "country_code", foreignKey = @ForeignKey(name = "fk_address_country"))
    @NotFound(action = NotFoundAction.EXCEPTION)
    @NotNull(message = "Country is mandatory")
    private Country country;

    @Column(name = "zip_code", length = 20)
    private String zipCode;

    @Column(name = "city")
    private String city;

    @Column(name = "state_or_province")
    private String stateOrProvince;

    @Column(name = "street")
    private String street;

}
