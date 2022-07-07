/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository;

import com.codecool.membershipmanagementapp.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CountryRepository extends JpaRepository<Country, String> {

    @Override
    Country save(Country country);

    Optional<Country> findCountryByNameOfCountryHunIgnoreCase(String name);
}
