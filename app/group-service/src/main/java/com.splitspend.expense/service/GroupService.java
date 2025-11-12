package com.splitspend.expense.service;

import com.splitspend.expense.controller.dto.CreateGroupRequestDto;
import com.splitspend.expense.controller.dto.GroupResponseDto;
import com.splitspend.expense.entity.Group;
import com.splitspend.expense.entity.GroupMember;
import com.splitspend.expense.entity.User;
import com.splitspend.expense.repository.GroupMemberRepository;
import com.splitspend.expense.repository.GroupRepository;
import com.splitspend.expense.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class GroupService {
    private final GroupRepository groupRepository;

    private final GroupMemberRepository groupMemberRepository;

    private final UserRepository userRepository;


    public GroupService(GroupRepository groupRepository, GroupMemberRepository groupMemberRepository, UserRepository userRepository) {
        this.groupRepository = groupRepository;
        this.groupMemberRepository = groupMemberRepository;
        this.userRepository = userRepository;
    }

    @Transactional
    public GroupResponseDto createGroup(Long ownerId, CreateGroupRequestDto createGroupRequestDto) {
        Group group = new Group();
        if(createGroupRequestDto.groupName() != null && !createGroupRequestDto.groupName().isBlank()) {
            group.setName(createGroupRequestDto.groupName());
        }
        group.setCreatedAt(Date.from(Instant.now()));
        Optional<User> owner = userRepository.findByEmail(createGroupRequestDto.email());
        group.setOwner(owner.get());
        Group savedGroup = saveGroup(group);


        List<String> members = new ArrayList<>();
        createGroupRequestDto.memberEmails().forEach(mail ->
                userRepository.findByEmail(mail)
                        .ifPresent(user -> {
                            GroupMember member = new GroupMember(savedGroup, user, LocalDateTime.now());
                            members.add(member.getUser().getEmail());
                            groupMemberRepository.save(member);
                        })
        );
        GroupMember ownerMember = new GroupMember(savedGroup, savedGroup.getOwner(), LocalDateTime.now());
        groupMemberRepository.save(ownerMember);
        members.add(savedGroup.getOwner().getEmail());

        /*createGroupRequestDto.memberEmails().stream() //functional way
                .map(userRepository::findByEmail)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(user -> new GroupMember(savedGroup, user, LocalDateTime.now()))
                .forEach(groupMemberRepository::save);*/

        return new GroupResponseDto(savedGroup.getId(), savedGroup.getName(), members);
    }

    public GroupResponseDto getGroupDetails(UUID userId) {
        Optional<Group> currentGroup = groupRepository.findById(userId);
        if (currentGroup.isPresent()) {
            //UUID id = currentGroup.get().getId();
            List<GroupMember> groupMembers =  groupMemberRepository.findByGroup(currentGroup.get());
            List<String> members = groupMembers.stream().map(m -> m.getUser().getEmail()).toList();
            return new GroupResponseDto(currentGroup.get().getId(), currentGroup.get().getName(), members);
        } else {
            throw new RuntimeException("Group not found");
        }
    }

    private Group saveGroup(Group group) {
        return groupRepository.save(group);
    }
}
