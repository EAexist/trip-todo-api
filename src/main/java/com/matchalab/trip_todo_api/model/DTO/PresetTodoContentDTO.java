package com.matchalab.trip_todo_api.model.DTO;

import lombok.Builder;

@Builder
public record PresetTodoContentDTO(
        Long id,
        String category,
        String type,
        String title,
        String iconId) {
}
