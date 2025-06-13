package com.matchalab.trip_todo_api.model.DTO;

import java.util.ArrayList;
import java.util.List;

import lombok.Builder;
import lombok.NonNull;

/* https://github.com/projectlombok/lombok/issues/3883 */
@Builder
public record TripDTO(
        Long id,
        String title,
        String startDateISOString,
        String endDateISOString,
        List<DestinationDTO> destination,
        List<TodoDTO> todolist,
        List<AccomodationDTO> accomodation) {

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
