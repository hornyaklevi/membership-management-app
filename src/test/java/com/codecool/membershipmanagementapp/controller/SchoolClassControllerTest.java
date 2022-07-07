/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.repository.command.CreateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.dto.AddressDto;
import com.codecool.membershipmanagementapp.repository.dto.CountryDto;
import com.codecool.membershipmanagementapp.repository.dto.SchoolClassDto;
import com.codecool.membershipmanagementapp.repository.dto.SchoolDto;
import com.codecool.membershipmanagementapp.service.SchoolClassService;
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
import java.util.List;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = SchoolClassController.class)
@ActiveProfiles("test")
class SchoolClassControllerTest {

    @MockBean
    private SchoolClassService schoolClassService;

    @Autowired
    private MockMvc mockMvc;

    private final List<AddressDto> addresses = new ArrayList<>();
    private final List<SchoolDto> schools = new ArrayList<>();
    private final List<SchoolClassDto> schoolClasses = new ArrayList<>();

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
    }

    @Test
    void testFindAll() throws Exception {

        when(schoolClassService.findAll())
                .thenReturn(schoolClasses);

        mockMvc.perform(get("/api/v1/classes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id", equalTo("PS2008B")))
                .andExpect(jsonPath("$[0].yearOfGraduation", equalTo(2008)))
                .andExpect(jsonPath("$[0].markOfClass", equalTo("B")))
                .andExpect(jsonPath("$[0].formTeacher", equalTo("Tóth Bertalan")))
                .andExpect(jsonPath("$[0].school.id", equalTo("PS")))
                .andExpect(jsonPath("$[1].id", equalTo("JA2020A")))
                .andExpect(jsonPath("$[1].yearOfGraduation", equalTo(2020)))
                .andExpect(jsonPath("$[1].markOfClass", equalTo("A")))
                .andExpect(jsonPath("$[1].school.id", equalTo("JA")))
                .andExpect(jsonPath("$[2].id", equalTo("KL2010C")))
                .andExpect(jsonPath("$[2].yearOfGraduation", equalTo(2010)))
                .andExpect(jsonPath("$[2].markOfClass", equalTo("C")))
                .andExpect(jsonPath("$[2].formTeacher", equalTo("Kelemen Olívia")))
                .andExpect(jsonPath("$[2].school.id", equalTo("KL")));

    }

    @Test
    void testFindById() throws Exception {

        when(schoolClassService.findById("PS2008B"))
                .thenReturn(schoolClasses.get(0));

        mockMvc.perform(get("/api/v1/class/{id}", "PS2008B"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("PS2008B")))
                .andExpect(jsonPath("$.yearOfGraduation", equalTo(2008)))
                .andExpect(jsonPath("$.markOfClass", equalTo("B")))
                .andExpect(jsonPath("$.formTeacher", equalTo("Tóth Bertalan")))
                .andExpect(jsonPath("$.school.id", equalTo("PS")));
    }

    @Test
    void testSave() throws Exception {

        when(schoolClassService.save(any()))
                .thenReturn(schoolClasses.get(1));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/class")
                        .content(asJsonString(new CreateSchoolClassCommand("JA2020A", (short) 2020, "A", "Győri Domokos", schools.get(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", equalTo("JA2020A")))
                .andExpect(jsonPath("$.yearOfGraduation", equalTo(2020)))
                .andExpect(jsonPath("$.markOfClass", equalTo("A")))
                .andExpect(jsonPath("$.formTeacher", equalTo("Győri Domokos")))
                .andExpect(jsonPath("$.school.id", equalTo("JA")));
    }

    @Test
    void testSaveWithInvalidCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/class")
                        .content(asJsonString(new CreateSchoolClassCommand("ja2020A", (short) 2020, "A", "Győri Domokos", schools.get(1))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate() throws Exception {

        String id = "PS2008B";
        when(schoolClassService.update(eq(id), any()))
                .thenReturn(new SchoolClassDto("PS2008B", (short) 2008, "B", "Benedek Alexander", schools.get(0)));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/class/" + id)
                        .content(asJsonString(new UpdateSchoolClassCommand((short) 2008, "B", "Benedek Alexander", schools.get(0))))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id", equalTo("PS2008B")))
                .andExpect(jsonPath("$.yearOfGraduation", equalTo(2008)))
                .andExpect(jsonPath("$.formTeacher", equalTo("Benedek Alexander")))
                .andExpect(jsonPath("$.school.id", equalTo("PS")));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/class/{id}", "PS2008B")
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