package com.matchalab.trip_todo_api.model.DTO;

import lombok.Builder;

@Builder
public record TodoDTO(
        Long id,
        int orderKey,
        String note,
        String completeDateISOString,
        Long presetId,
        String category,
        String type,
        String title,
        String iconId) {
}
