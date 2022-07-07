/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AddressTest {

    @Test
    void testAddress() {

        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");

        assertEquals("HU", address.getCountry().getCode());
        assertEquals("Magyarország", address.getCountry().getNameOfCountryHun());
        assertEquals("Hungary", address.getCountry().getNameOfCountryEng());
        assertEquals("8103", address.getZipCode());
        assertEquals("Várpalota", address.getCity());
        assertEquals("Veszprém megye", address.getStateOrProvince());
        assertEquals("Teréz krt. 17.", address.getStreet());

        address.setZipCode("1111");
        address.setCity("Budapest");
        address.setStateOrProvince("Budapest");
        address.setStreet("Margit krt. 29.");

        assertEquals("HU", address.getCountry().getCode());
        assertEquals("Magyarország", address.getCountry().getNameOfCountryHun());
        assertEquals("Hungary", address.getCountry().getNameOfCountryEng());
        assertEquals("1111", address.getZipCode());
        assertEquals("Budapest", address.getCity());
        assertEquals("Budapest", address.getStateOrProvince());
        assertEquals("Margit krt. 29.", address.getStreet());
    }
}