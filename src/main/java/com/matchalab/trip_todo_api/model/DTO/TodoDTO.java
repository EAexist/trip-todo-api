package com.matchalab.trip_todo_api.model.DTO;

import lombok.Builder;

@Builder
public record TodoDTO(
        Long id,
        int order_key,
        String note,
        String completeDateISOString,
        Boolean isPreset,
        String category,
        String type,
        String title,
        String iconId) {
}
