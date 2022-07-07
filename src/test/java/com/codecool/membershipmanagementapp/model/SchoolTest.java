/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import com.codecool.membershipmanagementapp.model.school.School;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;

class SchoolTest {

    @Test
    void testSchool() {
        Address address1 = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");

        School school = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address1, true, null);

        assertEquals("PS", school.getId());
        assertEquals((short) 1, school.getGroupId());
        assertEquals("Petőfi Sándor Gimnázium", school.getName());
        assertEquals(address1, school.getAddress());
        assertTrue(school.getIsActive());
        assertNull(school.getSchoolClasses());

        Address address2 = new Address(new Country("HU", "Magyarország", "Hungary"),
                "1180", "Budapest", "Budapest", "István utca 25.");

        school.setId("JA");
        school.setGroupId((short) 2);
        school.setName("József Attila Gimnázium");
        school.setAddress(address2);
        school.setIsActive(false);
        school.setSchoolClasses(new ArrayList<>());

        assertEquals("JA", school.getId());
        assertEquals((short) 2, school.getGroupId());
        assertEquals("József Attila Gimnázium", school.getName());
        assertEquals(address2, school.getAddress());
        assertFalse(school.getIsActive());
        assertEquals(Collections.emptyList(), school.getSchoolClasses());

    }

    @Test
    void testSchoolEquals() {
        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");

        School school1 = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        School school2 = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        School school3 = new School("JA", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);

        assertEquals(school1, school2);
        assertNotEquals(school1, school3);

        assertTrue(school1.equals(school1));
        assertTrue(school1.equals(school2));
        assertFalse(school1.equals(school3));
        assertFalse(school1.equals(null));
    }

    @Test
    void testSchoolHashCode() {
        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");

        School school1 = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        School school2 = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        School school3 = new School("JA", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);

        assertTrue(school1.hashCode() == school2.hashCode());
        assertFalse(school1.hashCode() != school3.hashCode());
        assertFalse(school2.hashCode() != school3.hashCode());
    }
}