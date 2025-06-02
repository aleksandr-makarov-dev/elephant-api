package com.github.elephant.taskmanagement.dto;

import com.github.elephant.taskmanagement.entity.BoardStatus;

import java.time.LocalDateTime;


public record BoardResponse(
        Long id,
        String title,
        String description,
        BoardStatus status,
        LocalDateTime createdAt
) {
}
