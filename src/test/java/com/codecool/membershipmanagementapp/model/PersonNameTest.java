/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import com.codecool.membershipmanagementapp.model.member.PersonName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PersonNameTest {

    @Test
    void testPersonName() {

        PersonName name = new PersonName("dr.", "Tóth", "István", "PhD", "Pisti");

        assertEquals("dr." , name.getPrefixOfName());
        assertEquals("Tóth" , name.getLastName());
        assertEquals("István" , name.getFirstName());
        assertEquals("PhD" , name.getSuffixOfName());
        assertEquals("Pisti" , name.getNickName());
        assertEquals("dr. Tóth István PhD" , name.getFullName());

        name.setPrefixOfName("ifj.");
        name.setLastName("Szabó");
        name.setFirstName("Mária");
        name.setSuffixOfName(null);
        name.setNickName("Marika");

        assertEquals("ifj." , name.getPrefixOfName());
        assertEquals("Szabó" , name.getLastName());
        assertEquals("Mária" , name.getFirstName());
        assertNull(name.getSuffixOfName());
        assertEquals("Marika" , name.getNickName());
        assertEquals("ifj. Szabó Mária" , name.getFullName());

    }

    @Test
    void testEquals() {
        PersonName name1 = new PersonName("dr.", "Tóth", "István", "PhD", "Pisti");
        PersonName name2 = new PersonName("dr.", "Tóth", "István", "PhD", "Pisti");
        PersonName name3 = new PersonName("ifj.", "Szabó", "Mária", null, "Marika");

        assertTrue(name1.equals(name1));
        assertTrue(name1.equals(name2));
        assertFalse(name1.equals(name3));
        assertFalse(name1.equals(null));
    }

    @Test
    void testHashCode() {
        PersonName name1 = new PersonName("dr.", "Tóth", "István", "PhD", "Pisti");
        PersonName name2 = new PersonName("dr.", "Tóth", "István", "PhD", "Pisti");
        PersonName name3 = new PersonName("ifj.", "Szabó", "Mária", null, "Marika");

        assertTrue(name1.hashCode() == name2.hashCode());
        assertTrue(name1.hashCode() != name3.hashCode());
        assertTrue(name2.hashCode() != name3.hashCode());
    }
}