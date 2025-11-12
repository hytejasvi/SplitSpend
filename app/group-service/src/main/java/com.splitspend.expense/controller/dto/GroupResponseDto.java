package com.splitspend.expense.controller.dto;

import java.util.List;
import java.util.UUID;

public record GroupResponseDto(
        UUID id,
        String name,
        List<String> members
) {
}
