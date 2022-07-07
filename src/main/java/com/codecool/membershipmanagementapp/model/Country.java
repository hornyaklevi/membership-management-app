/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "country")
public class Country {

    @Id
    @Column(name = "code", nullable = false, length = 2)
    private String code;

    @Column(name = "name_hu", nullable = false)
    private String nameOfCountryHun;

    @Column(name = "name_en", nullable = false)
    private String nameOfCountryEng;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Country country = (Country) o;
        return code != null && Objects.equals(code, country.code);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}
