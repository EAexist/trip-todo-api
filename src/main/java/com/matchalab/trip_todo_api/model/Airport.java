package com.matchalab.trip_todo_api.model;

import com.google.auto.value.AutoValue.Builder;

import jakarta.annotation.Nullable;

@Builder
public record Airport(
        String airportName,
        String cityName,
        String ISONationCode2Digit,
        @Nullable String iataCode) {
}
