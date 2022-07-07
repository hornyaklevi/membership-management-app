/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import com.codecool.membershipmanagementapp.model.school.School;
import com.codecool.membershipmanagementapp.model.school.SchoolClass;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class SchoolClassTest {

    @Test
    void testSchoolClass() {
        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");
        School school1 = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        SchoolClass schoolClass = new SchoolClass("PS2008B", (short) 2008, "B", "Tóth Bertalan", school1, new ArrayList<>());

        assertEquals("PS2008B", schoolClass.getId());
        assertEquals((short) 2008, schoolClass.getYearOfGraduation());
        assertEquals("B", schoolClass.getMarkOfClass());
        assertEquals("Tóth Bertalan", schoolClass.getFormTeacher());
        assertEquals(school1, schoolClass.getSchool());

        School school2 = new School("JA", (short) 2, "József Attila Gimnázium", null, true, null);

        schoolClass.setId("JA2020A");
        schoolClass.setYearOfGraduation((short) 2020);
        schoolClass.setMarkOfClass("A");
        schoolClass.setFormTeacher("Győri Domokos");
        schoolClass.setSchool(school2);

        assertEquals("JA2020A", schoolClass.getId());
        assertEquals((short) 2020, schoolClass.getYearOfGraduation());
        assertEquals("A", schoolClass.getMarkOfClass());
        assertEquals("Győri Domokos", schoolClass.getFormTeacher());
        assertEquals(school2, schoolClass.getSchool());
    }

    @Test
    void testSchoolClassEquals() {
        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");

        School school1 = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        School school2 = new School("JA", (short) 2, "József Attila Gimnázium", null, true, null);

        SchoolClass schoolClass1 = new SchoolClass("PS2008B", (short) 2008, "B", "Tóth Bertalan", school1, new ArrayList<>());
        SchoolClass schoolClass2 = new SchoolClass("PS2008B", (short) 2008, "B", "Tóth Bertalan", school1, new ArrayList<>());
        SchoolClass schoolClass3 = new SchoolClass("JA2020A", (short) 2020, "A", "Győri Domokos", school2, new ArrayList<>());

        assertTrue(schoolClass1.equals(schoolClass1));
        assertTrue(schoolClass1.equals(schoolClass2));
        assertFalse(schoolClass1.equals(schoolClass3));
        assertFalse(schoolClass1.equals(null));
    }

    @Test
    void testSchoolClassHashCode() {
        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");

        School school1 = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        School school2 = new School("JA", (short) 2, "József Attila Gimnázium", null, true, null);

        SchoolClass schoolClass1 = new SchoolClass("PS2008B", (short) 2008, "B", "Tóth Bertalan", school1, new ArrayList<>());
        SchoolClass schoolClass2 = new SchoolClass("PS2008B", (short) 2008, "B", "Tóth Bertalan", school1, new ArrayList<>());
        SchoolClass schoolClass3 = new SchoolClass("JA2020A", (short) 2020, "A", "Győri Domokos", school2, new ArrayList<>());

        assertTrue(schoolClass1.hashCode() == schoolClass2.hashCode());
        assertFalse(schoolClass1.hashCode() != schoolClass3.hashCode());
        assertFalse(schoolClass2.hashCode() != schoolClass3.hashCode());
    }

}