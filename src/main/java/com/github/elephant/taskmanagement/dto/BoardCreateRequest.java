package com.github.elephant.taskmanagement.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record BoardCreateRequest(
        @NotBlank(message = "Title must not be empty")
        @Size(min = 5, max = 100, message = "Title length must be between {min} and {max} characters")
        String title,

        @Size(min = 5, max = 250, message = "Description length must be between {min} and {max} characters")
        String description
) {}
