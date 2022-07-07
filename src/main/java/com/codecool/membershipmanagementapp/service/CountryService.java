/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.service;

import com.codecool.membershipmanagementapp.model.Country;
import com.codecool.membershipmanagementapp.repository.CountryRepository;
import com.codecool.membershipmanagementapp.repository.command.CreateCountryCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateCountryCommand;
import com.codecool.membershipmanagementapp.repository.dto.CountryDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class CountryService {

    private final CountryRepository countryRepository;
    private final ModelMapper modelMapper;

    public List<CountryDto> findAll() {
        Type targetListType = new TypeToken<List<CountryDto>>() {
        }.getType();
        return modelMapper.map(countryRepository.findAll(Sort.by(Sort.Direction.ASC, "code")), targetListType);
    }

    public CountryDto findByCode(String code) {
        return modelMapper.map(
                countryRepository.findById(code).orElseThrow(
                        () -> new IllegalArgumentException(String.format("Country with code %s not found.", code))),
                CountryDto.class);
    }

    public CountryDto findCountryByNameHun(String name) {
        return modelMapper.map(
                countryRepository.findCountryByNameOfCountryHunIgnoreCase(name)
                        .stream()
                        .findFirst()
                        .orElseThrow(
                                () -> new IllegalArgumentException(String.format("Country with name %s not found.", name))),
                CountryDto.class);
    }

    public CountryDto save(CreateCountryCommand command) {
        boolean countryExists = countryRepository.findById(command.getCode()).isPresent();

        if (countryExists) {
            throw new IllegalStateException(String.format("Country with code %s already taken.", command.getCode()));
        }

        Country country = modelMapper.map(command, Country.class);
        return modelMapper.map(countryRepository.save(country), CountryDto.class);
    }

    public void deleteById(String code) {
        countryRepository.deleteById(code);
    }

    public CountryDto update(String code, UpdateCountryCommand command) {
        Country countryToUpdate = countryRepository.findById(code)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Country with name %s not found.", code)));

        countryToUpdate.setNameOfCountryHun(command.getNameOfCountryHun());
        countryToUpdate.setNameOfCountryEng(command.getNameOfCountryEng());

        return modelMapper.map(countryRepository.save(countryToUpdate), CountryDto.class);
    }
}
