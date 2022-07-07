/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.repository;

import com.codecool.membershipmanagementapp.model.school.SchoolClass;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SchoolClassRepository extends JpaRepository<SchoolClass, String> {

    @EntityGraph(type = EntityGraph.EntityGraphType.FETCH, value = "schoolclass_entity_graph")
    List<SchoolClass> findAll();

    @Override
    SchoolClass save(SchoolClass schoolClass);
}