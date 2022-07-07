/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.service;

import com.codecool.membershipmanagementapp.model.school.SchoolClass;
import com.codecool.membershipmanagementapp.repository.SchoolClassRepository;
import com.codecool.membershipmanagementapp.repository.command.CreateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.dto.SchoolClassDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final ModelMapper modelMapper;

    public List<SchoolClassDto> findAll() {
        Type targetListType = new TypeToken<List<SchoolClassDto>>() {
        }.getType();
        return modelMapper.map(schoolClassRepository.findAll(), targetListType);
    }

    public SchoolClassDto findById(String id) {
        return modelMapper.map(
                schoolClassRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException(String.format("Class with id %s not found.", id))),
                SchoolClassDto.class);
    }

    public SchoolClassDto save(CreateSchoolClassCommand command) {
        boolean schoolClassExists = schoolClassRepository.findById(command.getId()).isPresent();

        if (schoolClassExists) {
            throw new IllegalStateException(String.format("Class with id %s already taken.", command.getId()));
        }

        SchoolClass schoolClass = modelMapper.map(command, SchoolClass.class);
        return modelMapper.map(schoolClassRepository.save(schoolClass), SchoolClassDto.class);
    }

    public void deleteById(String id) {
        schoolClassRepository.deleteById(id);
    }

    public SchoolClassDto update(String id, UpdateSchoolClassCommand command) {
        SchoolClass schoolClassToUpdate = schoolClassRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Class with id %s not found.", id)));

        SchoolClass schoolClassInput = modelMapper.map(command, SchoolClass.class);

        schoolClassToUpdate.setYearOfGraduation(schoolClassInput.getYearOfGraduation());
        schoolClassToUpdate.setMarkOfClass(schoolClassInput.getMarkOfClass());
        schoolClassToUpdate.setFormTeacher(schoolClassInput.getFormTeacher());
        schoolClassToUpdate.setSchool(schoolClassInput.getSchool());

        return modelMapper.map(schoolClassRepository.save(schoolClassToUpdate), SchoolClassDto.class);
    }
}
