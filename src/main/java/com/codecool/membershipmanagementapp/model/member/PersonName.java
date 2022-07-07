/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model.member;

import lombok.*;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonName {

    @Column(name = "prefix_of_name")
    private String prefixOfName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Column(name = "first_name", nullable = false)
    private String firstName;

    @Column(name = "suffix_of_name")
    private String suffixOfName;

    @Column(name = "nick_name")
    private String nickName;

    public String getFullName() {
        return Stream.of(prefixOfName, lastName, firstName, suffixOfName)
                .filter(n -> n != null && !n.isEmpty())
                .collect(Collectors.joining(" "));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PersonName that = (PersonName) o;
        return Objects.equals(prefixOfName, that.prefixOfName) && lastName.equals(that.lastName) && firstName.equals(that.firstName) && Objects.equals(suffixOfName, that.suffixOfName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(prefixOfName, lastName, firstName, suffixOfName);
    }
}
