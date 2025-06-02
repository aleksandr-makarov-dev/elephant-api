package com.github.elephant.management.dto;

import com.github.elephant.management.entity.BoardStatus;

import java.time.LocalDateTime;


public record BoardResponse(
        Long id,
        String title,
        String description,
        BoardStatus status,
        LocalDateTime createdAt
) {
}
