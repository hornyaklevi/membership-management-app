/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.repository.command.CreateSchoolCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateSchoolCommand;
import com.codecool.membershipmanagementapp.repository.dto.AddressDto;
import com.codecool.membershipmanagementapp.repository.dto.CountryDto;
import com.codecool.membershipmanagementapp.repository.dto.SchoolDto;
import com.codecool.membershipmanagementapp.service.SchoolService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SchoolController.class)
@ActiveProfiles("test")
class SchoolControllerTest {

    @MockBean
    private SchoolService schoolService;

    @Autowired
    private MockMvc mockMvc;

    private final List<AddressDto> addresses = new ArrayList<>();
    private final List<SchoolDto> schools = new ArrayList<>();

    @BeforeEach
    void setUp() {
        addresses.add(new AddressDto(new CountryDto("HU", "Magyarország", "Hungary"),
                "8103", "Várpalota", "Veszprém megye", "Teréz krt. 17."));
        addresses.add(new AddressDto(new CountryDto("HU", "Magyarország", "Hungary"),
                "1180", "Budapest", "Budapest", "István utca 25."));

        schools.add(new SchoolDto("PS", (short) 1, "Petőfi Sándor Gimnázium", addresses.get(0), true, null));
        schools.add(new SchoolDto("JA", (short) 2, "József Attila Gimnázium", addresses.get(1), false, new ArrayList<>()));
        schools.add(new SchoolDto("KL", (short) 2, "Kossuth Lajos Gimnázium", null, true, new ArrayList<>()));
    }

    @Test
    void testFindAll() throws Exception {

        when(schoolService.findAll())
                .thenReturn(schools);

        mockMvc.perform(get("/api/v1/schools"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo("PS")))
                .andExpect(jsonPath("$[0].name", equalTo("Petőfi Sándor Gimnázium")))
                .andExpect(jsonPath("$[0].isActive", equalTo(true)))
                .andExpect(jsonPath("$[1].id", equalTo("JA")))
                .andExpect(jsonPath("$[1].groupId", equalTo(2)))
                .andExpect(jsonPath("$[1].isActive", equalTo(false)))
                .andExpect(jsonPath("$[2].name", equalTo("Kossuth Lajos Gimnázium")))
                .andExpect(jsonPath("$[2].address", equalTo(null)))
                .andExpect(jsonPath("$[2].isActive", equalTo(true)));

    }

    @Test
    void testFindById() throws Exception {

        when(schoolService.findById("PS"))
                .thenReturn(schools.get(0));

        mockMvc.perform(get("/api/v1/school/{id}", "PS"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("PS")))
                .andExpect(jsonPath("$.groupId", equalTo(1)))
                .andExpect(jsonPath("$.name", equalTo("Petőfi Sándor Gimnázium")))
                .andExpect(jsonPath("$.isActive", equalTo(true)))
                .andExpect(jsonPath("$.classes", equalTo(null)));
    }

    @Test
    void testSave() throws Exception {

        when(schoolService.save(any()))
                .thenReturn(schools.get(1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/school")
                        .content(asJsonString(new CreateSchoolCommand("JA", (short) 2, "József Attila Gimnázium", addresses.get(1), false)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo("JA")))
                .andExpect(jsonPath("$.groupId", equalTo(2)))
                .andExpect(jsonPath("$.name", equalTo("József Attila Gimnázium")))
                .andExpect(jsonPath("$.address.city", equalTo("Budapest")))
                .andExpect(jsonPath("$.address.street", equalTo("István utca 25.")))
                .andExpect(jsonPath("$.isActive", equalTo(false)))
                .andExpect(jsonPath("$.classes", equalTo(Collections.emptyList())));
    }

    @Test
    void testSaveWithInvalidCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/school")
                        .content(asJsonString(new CreateSchoolCommand("ja", (short) 2, "József Attila Gimnázium", addresses.get(1), false)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate() throws Exception {

        String id = "PS";
        when(schoolService.update(eq(id), any()))
                .thenReturn(new SchoolDto("PS", (short) 2, "P.S. Gimnázium", addresses.get(0), false, null));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/school/" + id)
                        .content(asJsonString(new UpdateSchoolCommand((short) 2, "P.S. Gimnázium", addresses.get(0), false)))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.groupId", equalTo(2)))
                .andExpect(jsonPath("$.name", equalTo("P.S. Gimnázium")))
                .andExpect(jsonPath("$.isActive", equalTo(false)));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/school/{id}", "PS")
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }

    private static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}