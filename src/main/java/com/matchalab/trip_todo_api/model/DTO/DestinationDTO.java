package com.matchalab.trip_todo_api.model.DTO;

import lombok.Builder;

@Builder
public record DestinationDTO(
        Long id,
        String nation,
        String title,
        String region) {
}
