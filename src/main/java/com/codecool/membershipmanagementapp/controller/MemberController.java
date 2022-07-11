/*
 * Copyright (c) 2022. Levente Hornyák
 */

package com.codecool.membershipmanagementapp.controller;

import com.codecool.membershipmanagementapp.repository.command.CreateMemberCommand;
import com.codecool.membershipmanagementapp.repository.command.UpdateMemberCommand;
import com.codecool.membershipmanagementapp.repository.dto.MemberDto;
import com.codecool.membershipmanagementapp.service.MemberService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Tag( name = "Operations on members")
public record MemberController(MemberService memberService) {

    @GetMapping("/members")
    @Operation(summary = "List of all members",
            description = "List of all members.")
    public List<MemberDto> findAll() {
        return memberService.findAll();
    }

    @GetMapping("/member/{id}")
    @Operation(summary = "Find member by id",
            description = "Find member by id.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Invalid id format (NumberFormatException)")
    @ApiResponse(responseCode = "404", description = "Member not found")
    public MemberDto findById(
            @Parameter(description = "Id of the member", example = "1")
            @PathVariable("id") Long id) {
        return memberService.findById(id);
    }

    @PostMapping("/member")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Create a member",
            description = "Create a member.")
    @ApiResponse(responseCode = "201", description = "Created")
    @ApiResponse(responseCode = "400", description = "Invalid parameter")
    public MemberDto save(@Valid @RequestBody CreateMemberCommand command) {
        return memberService.save(command);
    }

    @DeleteMapping("/member/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete member by id",
            description = "Delete member by id.")
    @ApiResponse(responseCode = "204", description = "No Content")
    @ApiResponse(responseCode = "400", description = "Invalid id format (NumberFormatException)")
    @ApiResponse(responseCode = "404", description = "Class not found")
    public void deleteById(
            @Parameter(description = "Id of the member", example = "1")
            @PathVariable("id") Long id) {
        memberService.deleteById(id);
    }

    @PutMapping("/member/{id}")
    @Operation(summary = "Update member by id",
            description = "Update member by id.")
    @ApiResponse(responseCode = "200", description = "OK")
    @ApiResponse(responseCode = "400", description = "Invalid parameter")
    @ApiResponse(responseCode = "404", description = "Class not found")
    public MemberDto update(
            @Parameter(description = "Id of the member", example = "2")
            @PathVariable("id") Long id, @Valid @RequestBody UpdateMemberCommand command) {
        return memberService.update(id, command);
    }
}
