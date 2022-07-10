/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.service;

import com.codecool.membershipmanagementapp.model.Address;
import com.codecool.membershipmanagementapp.model.school.School;
import com.codecool.membershipmanagementapp.repository.SchoolRepository;
import com.codecool.membershipmanagementapp.repository.command.CreateSchoolCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateSchoolCommand;
import com.codecool.membershipmanagementapp.repository.dto.SchoolDto;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.lang.reflect.Type;
import java.util.List;

@Service
@AllArgsConstructor
public class SchoolService {

    private final SchoolRepository schoolRepository;
    private final ModelMapper modelMapper;
    private final EntityManagerFactory entityManagerFactory;

    public List<SchoolDto> findAll() {
        Type targetListType = new TypeToken<List<SchoolDto>>() {
        }.getType();
        return modelMapper.map(schoolRepository.findAll(Sort.by(Sort.Direction.ASC, "id")), targetListType);
    }

    public SchoolDto findById(String id) {
        return modelMapper.map(
                schoolRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException(String.format("School with id %s not found.", id))),
                SchoolDto.class);
    }

    public SchoolDto save(CreateSchoolCommand command) {
        boolean schoolExists = schoolRepository.findById(command.getId()).isPresent();

        if (schoolExists) {
            throw new IllegalStateException(String.format("School with id %s already taken.", command.getId()));
        }

        School school = modelMapper.map(command, School.class);
        return modelMapper.map(schoolRepository.save(school), SchoolDto.class);
    }

    public void deleteById(String id) {
        schoolRepository.deleteById(id);
    }

    public SchoolDto update(String id, UpdateSchoolCommand command) {

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();

        School schoolToUpdate = entityManager.find(School.class, id);
        if (schoolToUpdate == null) {
            throw new IllegalArgumentException(String.format("School with id %s not found.", id));
        }

        schoolToUpdate.setGroupId(command.getGroupId());
        schoolToUpdate.setName(command.getName());
        schoolToUpdate.setAddress(modelMapper.map(command.getAddress(), Address.class));
        schoolToUpdate.setIsActive(command.getIsActive());

        entityManager.merge(schoolToUpdate);
        entityManager.getTransaction().commit();
        entityManager.close();

        return findById(id);
    }
}
