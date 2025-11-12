package com.splitspend.expense.controller.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CreateGroupRequestDto(
        @NotBlank String groupName,
        @NotNull @Email String email,
        List<@Email String> memberEmails) {
}
