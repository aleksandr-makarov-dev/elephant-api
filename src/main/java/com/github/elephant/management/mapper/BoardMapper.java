package com.github.elephant.management.mapper;

import com.github.elephant.management.dto.BoardCreateRequest;
import com.github.elephant.management.dto.BoardResponse;
import com.github.elephant.management.entity.BoardEntity;
import org.springframework.stereotype.Component;

@Component
public class BoardMapper {
    public BoardResponse toBoardResponse(BoardEntity entity) {
        return new BoardResponse(
                entity.getId(),
                entity.getTitle(),
                entity.getDescription(),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    public BoardEntity toBoardEntity(BoardCreateRequest request) {
        return BoardEntity.builder()
                .title(request.title())
                .description(request.description())
                .status(request.status())
                .build();
    }
}
