package com.matchalab.trip_todo_api.model.DTO;

import java.util.Map;

import lombok.Builder;

@Builder
public record AccomodationDTO(
        Long id,
        String title,
        String roomTitle,
        int numberofClient,
        String clientName,
        String checkinDateISOString,
        String checkoutDateISOString,
        String checkinStartTimeISOString,
        String checkinEndTimeISOString,
        String checkoutTimeISOString,
        String region,
        String type,
        Map<String, String> links) {
}
