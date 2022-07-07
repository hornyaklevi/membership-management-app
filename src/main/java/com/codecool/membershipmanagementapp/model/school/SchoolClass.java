/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model.school;

import com.codecool.membershipmanagementapp.model.member.Member;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "schoolclass")
@NamedEntityGraph(name = "schoolclass_entity_graph", attributeNodes = @NamedAttributeNode("members"))
public class SchoolClass {

    @Id
    @Column(name = "id", nullable = false, length = 7)
    private String id;

    @Column(name = "year_of_graduation", nullable = false)
    private Short yearOfGraduation;

    @Column(name = "mark_of_class", length = 1, nullable = false)
    private String markOfClass;

    @Column(name = "form_teacher")
    private String formTeacher;

    @ManyToOne
    @JoinColumn(name = "school_id", nullable = false, foreignKey = @ForeignKey(name = "fk_class_school"))
    @JsonIgnoreProperties(value = {"address", "classes"})
    private School school;

    @OneToMany(mappedBy = "schoolClass")
    private List<Member> members;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        SchoolClass that = (SchoolClass) o;
        return id != null && Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

}