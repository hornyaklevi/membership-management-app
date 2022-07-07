/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CountryTest {

    @Test
    void testCountry() {
        Country country = new Country("HU", "Magyarország", "Hungary");

        assertEquals("HU", country.getCode());
        assertEquals("Magyarország", country.getNameOfCountryHun());
        assertEquals("Hungary", country.getNameOfCountryEng());

        country.setCode("DE");
        country.setNameOfCountryHun("Németország");
        country.setNameOfCountryEng("Germany");

        assertEquals("DE", country.getCode());
        assertEquals("Németország", country.getNameOfCountryHun());
        assertEquals("Germany", country.getNameOfCountryEng());
    }

    @Test
    void testCountryEquals() {
        Country country1 = new Country("HU", "Magyarország", "Hungary");
        Country country2 = new Country("HU", "Magyarország", "Hungary");
        Country country3 = new Country("DE", "Németország", "Germany");
        Country country4 = new Country();

        assertTrue(country1.equals(country1));
        assertTrue(country1.equals(country2));
        assertFalse(country1.equals(country3));
        assertFalse(country2.equals(country3));
        assertFalse(country3.equals(country4));
        assertFalse(country1.equals(null));
    }

    @Test
    void testCountryHashCode() {
        Country country1 = new Country("HU", "Magyarország", "Hungary");
        Country country2 = new Country("HU", "Magyarország", "Hungary");
        Country country3 = new Country("DE", "Németország", "Germany");
        Country country4 = new Country();

        assertTrue(country1.hashCode() == country2.hashCode());
        assertFalse(country1.hashCode() != country3.hashCode());
        assertFalse(country2.hashCode() != country3.hashCode());
        assertFalse(country3.hashCode() != country4.hashCode());

    }
}