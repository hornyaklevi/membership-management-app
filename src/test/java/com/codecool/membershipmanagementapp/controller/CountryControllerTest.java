/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.repository.command.CreateCountryCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateCountryCommand;
import com.codecool.membershipmanagementapp.repository.dto.CountryDto;
import com.codecool.membershipmanagementapp.service.CountryService;
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

@WebMvcTest(controllers = CountryController.class)
@ActiveProfiles("test")
class CountryControllerTest {

    @MockBean
    private CountryService countryService;

    @Autowired
    private MockMvc mockMvc;

    private final List<CountryDto> countries = new ArrayList<>();

    @BeforeEach
    void setUp() {
        countries.add(new CountryDto("HU", "Magyarország", "Hungary"));
        countries.add(new CountryDto("DE", "Németország", "Germany"));
        countries.add(new CountryDto("ES", "Spanyolország", "Spain"));
        countries.add(new CountryDto("FI", "Finnország", "Finland"));
        countries.add(new CountryDto("GP", "Guadeloupe", "Guadeloupe"));
    }

    @Test
    void testFindAll() throws Exception {

        when(countryService.findAll())
                .thenReturn(countries);

        mockMvc.perform(get("/api/v1/countries"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].code", equalTo("HU")))
                .andExpect(jsonPath("$[0].nameOfCountryHun", equalTo("Magyarország")))
                .andExpect(jsonPath("$[0].nameOfCountryEng", equalTo("Hungary")))
                .andExpect(jsonPath("$[1].code", equalTo("DE")))
                .andExpect(jsonPath("$[1].nameOfCountryHun", equalTo("Németország")))
                .andExpect(jsonPath("$[1].nameOfCountryEng", equalTo("Germany")));
    }

    @Test
    void testFindByCode() throws Exception {

        when(countryService.findByCode("HU"))
                .thenReturn(new CountryDto("HU", "Magyarország", "Hungary"));

        mockMvc.perform(get("/api/v1/country/{code}", "HU"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("HU")))
                .andExpect(jsonPath("$.nameOfCountryHun", equalTo("Magyarország")))
                .andExpect(jsonPath("$.nameOfCountryEng", equalTo("Hungary")));
    }

    @Test
    void testFindCountryByNameHun() throws Exception {

        when(countryService.findCountryByNameHun("Magyarország"))
                .thenReturn(new CountryDto("HU", "Magyarország", "Hungary"));

        mockMvc.perform(get("/api/v1/country")
                        .param("name_hu", "Magyarország"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("HU")))
                .andExpect(jsonPath("$.nameOfCountryHun", equalTo("Magyarország")))
                .andExpect(jsonPath("$.nameOfCountryEng", equalTo("Hungary")));
    }

    @Test
    void testSave() throws Exception {

        when(countryService.save(any()))
                .thenReturn(new CountryDto("ZW", "Zimbabwe", "Zimbabwe"));

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/country")
                        .content(asJsonString(new CreateCountryCommand("ZW", "Zimbabwe", "Zimbabwe")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.code", equalTo("ZW")))
                .andExpect(jsonPath("$.nameOfCountryHun", equalTo("Zimbabwe")))
                .andExpect(jsonPath("$.nameOfCountryEng", equalTo("Zimbabwe")));
    }

    @Test
    void testSaveWithInvalidCode() throws Exception {

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/api/v1/country")
                        .content(asJsonString(new CreateCountryCommand("zw", "Zimbabwe", "Zimbabwe")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testUpdate() throws Exception {

        String code = "HU";
        when(countryService.update(eq(code), any()))
                .thenReturn(new CountryDto("HU", "Magyaro.", "Hungary"));

        mockMvc.perform(MockMvcRequestBuilders
                        .put("/api/v1/country/" + code)
                        .content(asJsonString(new UpdateCountryCommand("Magyaro.", "Hungary")))
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.code", equalTo("HU")))
                .andExpect(jsonPath("$.nameOfCountryHun", equalTo("Magyaro.")))
                .andExpect(jsonPath("$.nameOfCountryEng", equalTo("Hungary")));
    }

    @Test
    void testDelete() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/api/v1/country/{code}", "HU")
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