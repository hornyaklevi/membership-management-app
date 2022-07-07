/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model;

import com.codecool.membershipmanagementapp.model.member.Member;
import com.codecool.membershipmanagementapp.model.member.MembershipStatus;
import com.codecool.membershipmanagementapp.model.member.MembershipType;
import com.codecool.membershipmanagementapp.model.member.PersonName;
import com.codecool.membershipmanagementapp.model.school.School;
import com.codecool.membershipmanagementapp.model.school.SchoolClass;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

class MemberTest {

    @Test
    void testMember() {
        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");
        School school = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        SchoolClass schoolClass = new SchoolClass("PS2008B", (short) 2008, "B", "Tóth Bertalan", school, new ArrayList<>());

        LocalDateTime currentDateTime = LocalDateTime.now();

        PersonName name = new PersonName("dr.", "Tóth", "István", "PhD", "Pisti");
        Member member = new Member(1L, MembershipStatus.ACTIVE, MembershipType.REGULAR, name,
                "Budapest", LocalDate.of(1990, 1, 1), schoolClass, address, "example@example.com",
                "+36301234567", "any comment", true, currentDateTime, currentDateTime);

        assertEquals(1L, member.getMemberId());
        assertEquals(MembershipStatus.ACTIVE, member.getMembershipStatus());
        assertEquals(MembershipType.REGULAR, member.getMembershipType());
        assertEquals(name, member.getPersonName());
        assertEquals("Budapest", member.getPlaceOfBirth());
        assertEquals(LocalDate.of(1990, 1, 1), member.getDateOfBirth());
        assertEquals(schoolClass, member.getSchoolClass());
        assertEquals(address, member.getAddress());
        assertEquals("example@example.com", member.getEmail());
        assertEquals("+36301234567", member.getPhoneNumber());
        assertEquals("any comment", member.getComment());
        assertTrue(member.getIsAllowNewsletter());
        assertEquals(currentDateTime, member.getCreatedAt());
        assertEquals(currentDateTime, member.getUpdatedAt());

    }

    @Test
    void testMember2() {
        Address address = new Address(new Country("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17.");
        School school = new School("PS", (short) 1, "Petőfi Sándor Gimnázium", address, true, null);
        SchoolClass schoolClass = new SchoolClass("PS2008B", (short) 2008, "B", "Tóth Bertalan", school, new ArrayList<>());

        LocalDateTime currentDateTime = LocalDateTime.now();

        PersonName name = new PersonName("dr.", "Tóth", "István", "PhD", "Pisti");
        Member member = new Member(1L, MembershipStatus.ACTIVE, MembershipType.REGULAR, name,
                "Budapest", LocalDate.of(1990, 1, 1), schoolClass, address, "example@example.com",
                "+36301234567", "any comment", true, currentDateTime, currentDateTime);

        LocalDateTime updatedAt = LocalDateTime.now();

        member.setMemberId(2L);
        member.setMembershipStatus(MembershipStatus.INACTIVE);
        member.setMembershipType(MembershipType.HONORARY);
        member.setPersonName(name);
        member.setPlaceOfBirth("Budapest");
        member.setDateOfBirth(LocalDate.of(1990, 1, 1));
        member.setSchoolClass(schoolClass);
        member.setAddress(address);
        member.setEmail("newemail@example.com");
        member.setPhoneNumber("+36307654321");
        member.setComment("no comment");
        member.setIsAllowNewsletter(false);
        member.setCreatedAt(currentDateTime);
        member.setUpdatedAt(updatedAt);

        assertEquals(2L, member.getMemberId());
        assertEquals(MembershipStatus.INACTIVE, member.getMembershipStatus());
        assertEquals(MembershipType.HONORARY, member.getMembershipType());
        assertEquals(name, member.getPersonName());
        assertEquals("Budapest", member.getPlaceOfBirth());
        assertEquals(LocalDate.of(1990, 1, 1), member.getDateOfBirth());
        assertEquals(schoolClass, member.getSchoolClass());
        assertEquals(address, member.getAddress());
        assertEquals("newemail@example.com", member.getEmail());
        assertEquals("+36307654321", member.getPhoneNumber());
        assertEquals("no comment", member.getComment());
        assertFalse(member.getIsAllowNewsletter());
        assertEquals(currentDateTime, member.getCreatedAt());
        assertEquals(updatedAt, member.getUpdatedAt());
    }
}