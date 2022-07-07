/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.model.member.MembershipStatus;
import com.codecool.membershipmanagementapp.model.member.MembershipType;
import com.codecool.membershipmanagementapp.repository.command.CreateMemberCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateMemberCommand;
import com.codecool.membershipmanagementapp.repository.dto.*;
import com.codecool.membershipmanagementapp.service.MemberService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = MemberController.class)
@ActiveProfiles("test")
class MemberControllerTest {

    @MockBean
    MemberService memberService;

    @Autowired
    private MockMvc mockMvc;

    private final List<AddressDto> addresses = new ArrayList<>();
    private final List<SchoolDto> schools = new ArrayList<>();
    private final List<SchoolClassDto> schoolClasses = new ArrayList<>();
    private final List<MemberDto> members = new ArrayList<>();
    private final LocalDateTime currentDateTime = LocalDateTime.now();

    @BeforeEach
    void setUp() {

        addresses.add(new AddressDto(new CountryDto("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17."));
        addresses.add(new AddressDto(new CountryDto("HU", "Magyarország", "Hungary"),
                "1180", "Budapest", "Budapest", "István utca 25."));

        schools.add(new SchoolDto("PS", (short) 1, "Petőfi Sándor Gimnázium", addresses.get(0), true, null));
        schools.add(new SchoolDto("JA", (short) 2, "József Attila Gimnázium", addresses.get(1), false, new ArrayList<>()));
        schools.add(new SchoolDto("KL", (short) 2, "Kossuth Lajos Gimnázium", null, true, new ArrayList<>()));

        schoolClasses.add(new SchoolClassDto("PS2008B", (short) 2008, "B", "Tóth Bertalan", schools.get(0)));
        schoolClasses.add(new SchoolClassDto("JA2020A", (short) 2020, "A", "Győri Domokos", schools.get(1)));
        schoolClasses.add(new SchoolClassDto("KL2010C", (short) 2010, "C", "Kelemen Olívia", schools.get(2)));

        members.add(new MemberDto(1L, MembershipStatus.ACTIVE, MembershipType.REGULAR,
                new PersonNameDto("dr.", "Tóth", "István", "PhD", "Pisti"),
                "Budapest", LocalDate.of(1990, 1, 1), schoolClasses.get(0), addresses.get(0),
                "example@example.com", "+36301234567", null, true, currentDateTime, currentDateTime));
        members.add(new MemberDto(2L, MembershipStatus.INACTIVE, MembershipType.HONORARY,
                new PersonNameDto(null, "Kiss", "Géza", null, null),
                "Szeged", LocalDate.of(1985, 6, 30), schoolClasses.get(1), addresses.get(1),
                "example2@example.com", "+3645123456", "no comment", false, currentDateTime, currentDateTime));
        members.add(new MemberDto(3L, MembershipStatus.RIP, MembershipType.CONTRIBUTING,
                new PersonNameDto(null, "Nagy", "Mihály", null, "Misi"),
                "Debrecen", LocalDate.of(1950, 9, 14), schoolClasses.get(2), addresses.get(1),
                "example3@example.com", null, null, false, currentDateTime, currentDateTime));
    }

    @Test
    void testfindAll() throws Exception {

        when(memberService.findAll())
                .thenReturn(members);

        mockMvc.perform(get("/api/v1/members"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].memberId", equalTo(1)))
                .andExpect(jsonPath("$[0].name.firstName", equalTo("István")))
                .andExpect(jsonPath("$[0].placeOfBirth", equalTo("Budapest")))
                .andExpect(jsonPath("$[0].email", equalTo("example@example.com")))
                .andExpect(jsonPath("$[1].memberId", equalTo(2)))
                .andExpect(jsonPath("$[1].name.lastName", equalTo("Kiss")))
                .andExpect(jsonPath("$[1].placeOfBirth", equalTo("Szeged")))
                .andExpect(jsonPath("$[1].email", equalTo("example2@example.com")))
                .andExpect(jsonPath("$[1].isAllowNewsletter", equalTo(false)))
                .andExpect(jsonPath("$[2].memberId", equalTo(3)))
                .andExpect(jsonPath("$[2].status", equalTo("RIP")))
                .andExpect(jsonPath("$[2].name.nickName", equalTo("Misi")))
                .andExpect(jsonPath("$[2].placeOfBirth", equalTo("Debrecen")))
                .andExpect(jsonPath("$[2].email", equalTo("example3@example.com")))
                .andExpect(jsonPath("$[2].phoneNumber", equalTo(null)));

    }

    @Test
    void testfindById() throws Exception {

        when(memberService.findById(1L))
                .thenReturn(members.get(0));

        mockMvc.perform(get("/api/v1/member/{id}", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId", equalTo(1)))
                .andExpect(jsonPath("$.name.firstName", equalTo("István")))
                .andExpect(jsonPath("$.placeOfBirth", equalTo("Budapest")))
                .andExpect(jsonPath("$.email", equalTo("example@example.com")))
                .andExpect(jsonPath("$.phoneNumber", equalTo("+36301234567")))
                .andExpect(jsonPath("$.isAllowNewsletter", equalTo(true)));
    }

    @Test
    void testSave() throws Exception {

        CreateMemberCommand command = new CreateMemberCommand(MembershipStatus.INACTIVE, MembershipType.HONORARY,
                new PersonNameDto(null, "Kiss", "Géza", null, null),
                "Szeged", LocalDate.of(1985, 6, 30), schoolClasses.get(1), addresses.get(1),
                "example2@example.com", "+3645123456", "no comment", false);

        when(memberService.save(any()))
                .thenReturn(members.get(1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/member")
                        .content(asJsonString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.memberId", equalTo(2)))
                .andExpect(jsonPath("$.name.firstName", equalTo("Géza")))
                .andExpect(jsonPath("$.placeOfBirth", equalTo("Szeged")))
                .andExpect(jsonPath("$.email", equalTo("example2@example.com")))
                .andExpect(jsonPath("$.phoneNumber", equalTo("+3645123456")))
                .andExpect(jsonPath("$.isAllowNewsletter", equalTo(false)))
                .andExpect(jsonPath("$.class.id", equalTo("JA2020A")));
    }

    @Test
    void testSaveWithInvalidDateOfBirth() throws Exception {

        CreateMemberCommand command = new CreateMemberCommand(MembershipStatus.INACTIVE, MembershipType.HONORARY,
                new PersonNameDto(null, "Kiss", "Géza", null, null),
                "Szeged", LocalDate.of(2024, 6, 30), schoolClasses.get(1), addresses.get(1),
                "example2@example.com", "+3645123456", "no comment", false);

        when(memberService.save(any()))
                .thenReturn(members.get(1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/member")
                        .content(asJsonString(command))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testDeleteById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/member/{id}", "1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    @Test
    void testUpdate() throws Exception {
        Long id = 2L;
        LocalDateTime updatedAt = LocalDateTime.now();

        when(memberService.update(eq(id), any()))
                .thenReturn(new MemberDto(2L, MembershipStatus.ACTIVE, MembershipType.REGULAR,
                        new PersonNameDto(null, "Kiss", "Géza", null, null),
                        "Szeged", LocalDate.of(1985, 6, 30), schoolClasses.get(1), addresses.get(1),
                        "example5@example.com", "+3645123456", "no comment", true, currentDateTime, updatedAt));

        UpdateMemberCommand updateMemberCommand = new UpdateMemberCommand(MembershipStatus.ACTIVE, MembershipType.REGULAR,
                new PersonNameDto(null, "Kiss", "Géza", null, null),
                "Szeged", LocalDate.of(1985, 6, 30), schoolClasses.get(1), addresses.get(1),
                "example5@example.com", "+3645123456", "no comment", true);

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/member/" + id)
                        .content(asJsonString(updateMemberCommand))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.memberId", equalTo(2)))
                .andExpect(jsonPath("$.name.firstName", equalTo("Géza")))
                .andExpect(jsonPath("$.placeOfBirth", equalTo("Szeged")))
                .andExpect(jsonPath("$.email", equalTo("example5@example.com")))
                .andExpect(jsonPath("$.phoneNumber", equalTo("+3645123456")))
                .andExpect(jsonPath("$.isAllowNewsletter", equalTo(true)))
                .andExpect(jsonPath("$.class.id", equalTo("JA2020A")));
    }

    private static String asJsonString(final Object obj) {
        try {
            ObjectMapper mapper = new ObjectMapper();
            mapper.registerModule(new JavaTimeModule());
            return mapper.writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}