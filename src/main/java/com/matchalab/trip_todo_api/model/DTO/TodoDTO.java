package com.matchalab.trip_todo_api.model.DTO;

import com.matchalab.trip_todo_api.model.Icon;
import com.matchalab.trip_todo_api.model.Location;

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
        Icon icon,
        Location departure,
        Location arrival) {
}
