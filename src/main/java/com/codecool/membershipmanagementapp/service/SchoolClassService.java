/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.service;

import com.codecool.membershipmanagementapp.controller.exception.SchoolClassAlreadyTakenException;
import com.codecool.membershipmanagementapp.controller.exception.SchoolClassByIdNotFoundException;
import com.codecool.membershipmanagementapp.model.school.School;
import com.codecool.membershipmanagementapp.model.school.SchoolClass;
import com.codecool.membershipmanagementapp.repository.SchoolClassRepository;
import com.codecool.membershipmanagementapp.repository.command.CreateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateSchoolClassCommand;
import com.codecool.membershipmanagementapp.repository.dto.SchoolClassDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class SchoolClassService {

    private final SchoolClassRepository schoolClassRepository;
    private final ModelMapper modelMapper;
    private final EntityManagerFactory entityManagerFactory;

    public List<SchoolClassDto> findAll() {
        Type targetListType = new TypeToken<List<SchoolClassDto>>() {
        }.getType();
        return modelMapper.map(schoolClassRepository.findAll(), targetListType);
    }

    public SchoolClassDto findById(String id) {
        return modelMapper.map(
                schoolClassRepository.findById(id).orElseThrow(
                        () -> new SchoolClassByIdNotFoundException(id)),
                SchoolClassDto.class);
    }

    public SchoolClassDto save(CreateSchoolClassCommand command) {
        if (schoolClassRepository.existsById(command.getId())) {
            throw new SchoolClassAlreadyTakenException(command.getId());
        }

        SchoolClass schoolClass = modelMapper.map(command, SchoolClass.class);
        return modelMapper.map(schoolClassRepository.save(schoolClass), SchoolClassDto.class);
    }

    public void deleteById(String id) {
        if (!schoolClassRepository.existsById(id)) {
            throw new SchoolClassByIdNotFoundException(id);
        }

        schoolClassRepository.deleteById(id);
    }

    public SchoolClassDto update(String id, UpdateSchoolClassCommand command) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        SchoolClass schoolClassToUpdate = entityManager.find(SchoolClass.class, id);
        if (schoolClassToUpdate == null) {
            throw new SchoolClassByIdNotFoundException(id);
        }

        schoolClassToUpdate.setYearOfGraduation(command.getYearOfGraduation());
        schoolClassToUpdate.setMarkOfClass(command.getMarkOfClass());
        schoolClassToUpdate.setFormTeacher(command.getFormTeacher());
        schoolClassToUpdate.setSchool(modelMapper.map(command.getSchool(), School.class));

        entityManager.merge(schoolClassToUpdate);
        entityManager.getTransaction().commit();
        entityManager.close();

        return findById(id);
    }
}
