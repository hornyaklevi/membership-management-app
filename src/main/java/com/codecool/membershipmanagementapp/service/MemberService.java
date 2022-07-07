/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.service;

import com.codecool.membershipmanagementapp.model.member.Member;
import com.codecool.membershipmanagementapp.repository.MemberRepository;
import com.codecool.membershipmanagementapp.repository.command.CreateMemberCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateMemberCommand;
import com.codecool.membershipmanagementapp.repository.dto.MemberDto;
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
public class MemberService {

    private final MemberRepository memberRepository;
    private final ModelMapper modelMapper;
    private EntityManagerFactory entityManagerFactory;

    public List<MemberDto> findAll() {
        Type targetListType = new TypeToken<List<MemberDto>>() {
        }.getType();
        return modelMapper.map(memberRepository.findAll(), targetListType);
    }

    public MemberDto findById(Long id) {
        return modelMapper.map(
                memberRepository.findById(id).orElseThrow(
                        () -> new IllegalArgumentException(String.format("Member with id %d not found.", id))),
                MemberDto.class);
    }

    public MemberDto save(CreateMemberCommand command) {

        Member memberToSave = modelMapper.map(command, Member.class);

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(memberToSave);
        entityManager.getTransaction().commit();
        entityManager.close();

        return findById(memberToSave.getMemberId());
    }

    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public MemberDto update(Long id, UpdateMemberCommand command) {
        Member memberToUpdate = memberRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(String.format("Member with id %d not found.", id)));

        Member memberInput = modelMapper.map(command, Member.class);

        memberToUpdate.setMembershipStatus(memberInput.getMembershipStatus());
        memberToUpdate.setMembershipType(memberInput.getMembershipType());
        memberToUpdate.setPersonName(memberInput.getPersonName());
        memberToUpdate.setPlaceOfBirth(memberInput.getPlaceOfBirth());
        memberToUpdate.setDateOfBirth(memberInput.getDateOfBirth());
        memberToUpdate.setSchoolClass(memberInput.getSchoolClass());
        memberToUpdate.setAddress(memberInput.getAddress());
        memberToUpdate.setEmail(memberInput.getEmail());
        memberToUpdate.setPhoneNumber(memberInput.getPhoneNumber());
        memberToUpdate.setComment(memberInput.getComment());
        memberToUpdate.setIsAllowNewsletter(memberInput.getIsAllowNewsletter());

        return modelMapper.map(memberRepository.save(memberToUpdate), MemberDto.class);
    }
}
