package com.github.elephant.management.dto;

import com.github.elephant.management.entity.BoardStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record BoardUpdateRequest(
        @NotBlank(message = "Title must not be empty")
        @Size(min = 5, max = 100, message = "Title length must be between {min} and {max} characters")
        String title,

        @Size(min = 5, max = 250, message = "Description length must be between {min} and {max} characters")
        String description,

        @NotNull(message = "Status must be specified")
        BoardStatus status
) {
}
