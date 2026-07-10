package com.example.taskmanager.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record TaskDto(
        Long id,
        @NotBlank
        @Size(min = 3, max = 100)
        String name,
        @Size(max = 500)
        String description
) {}
