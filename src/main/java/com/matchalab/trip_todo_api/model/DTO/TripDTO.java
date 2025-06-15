package com.matchalab.trip_todo_api.model.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.NonNull;
import jakarta.annotation.Nullable;

/* https://github.com/projectlombok/lombok/issues/3883 */
@Builder
public record TripDTO(
        @Nullable Long id,
        @Nullable String title,
        @Nullable String startDateISOString,
        @Nullable String endDateISOString,
        @Nullable List<DestinationDTO> destination,
        @Nullable List<TodoDTO> todolist,
        @Nullable List<AccomodationDTO> accomodation) {

    public TripDTO(Long id,
            String title,
            String startDateISOString,
            String endDateISOString,
            List<DestinationDTO> destination,
            List<TodoDTO> todolist,
            List<AccomodationDTO> accomodation) {
        this.id = id;
        this.title = title;
        this.startDateISOString = startDateISOString;
        this.endDateISOString = endDateISOString;
        this.destination = destination;
        this.todolist = todolist;
        this.accomodation = accomodation;
    }
}
