/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.model.member;

import com.codecool.membershipmanagementapp.model.Address;
import com.codecool.membershipmanagementapp.model.school.SchoolClass;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.Hibernate;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "member")
@JsonPropertyOrder({"memberId", "name", "class", "placeOfBirth", "dateOfBirth", "address", "email", "phoneNumber",
        "status", "type", "comment"})
public class Member {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "member_id", nullable = false)
    private Long memberId;

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "status")
    @Column(name = "status", nullable = false)
    private MembershipStatus membershipStatus;

    @Enumerated(EnumType.STRING)
    @JsonProperty(value = "type")
    @Column(name = "type", nullable = false)
    private MembershipType membershipType;

    @Embedded
    @JsonProperty(value = "name")
    private PersonName personName;

    @Column(name = "place_of_birth")
    private String placeOfBirth;

    @Column(name = "date_of_birth")
    private LocalDate dateOfBirth;

    @ManyToOne
    @JoinColumn(name = "school_class_id", foreignKey = @ForeignKey(name = "fk_member_class"))
    @JsonProperty(value = "class")
    private SchoolClass schoolClass;

    @Embedded
    private Address address;

    @Column(name = "email")
    private String email;

    @Column(name = "phone_number")
    private String phoneNumber;

    @Column(name = "comment")
    private String comment;

    @Column(name = "is_allow_newsletter")
    private Boolean isAllowNewsletter;

    @Column(name = "created_at", updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Member member = (Member) o;
        return memberId != null && Objects.equals(memberId, member.memberId);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}