package com.splitspend.expense.controller;

import com.splitspend.expense.controller.dto.CreateGroupRequestDto;
import com.splitspend.expense.controller.dto.GroupResponseDto;
import com.splitspend.expense.service.GroupService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/groups")
public class GroupController {

    private final GroupService service;

    public GroupController(GroupService service) {
        this.service = service;
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponseDto> createGroup(@Valid @RequestBody CreateGroupRequestDto requestDto) {
        GroupResponseDto response = service.createGroup(52L, requestDto);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/{groupId}")
    public ResponseEntity<GroupResponseDto> getGroupDetails(@Valid @PathVariable UUID groupId) {
        GroupResponseDto responseDto = service.getGroupDetails(groupId);
        return ResponseEntity.ok(responseDto);
    }
}
