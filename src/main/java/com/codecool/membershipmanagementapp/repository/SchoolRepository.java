/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository;

import com.codecool.membershipmanagementapp.model.school.School;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolRepository extends JpaRepository<School, String> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "school_entity_graph")
    List<School> findAll();

    @Override
    School save(School school);
}