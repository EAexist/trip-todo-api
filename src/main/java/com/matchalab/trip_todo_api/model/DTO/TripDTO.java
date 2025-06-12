package com.matchalab.trip_todo_api.model.DTO;

import java.util.List;

import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.Todo;

import lombok.Builder;

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

}

// public class TripDTO {

// private Long id;

// private List<Destination> destinations = new ArrayList<Destination>();

// private List<Todo> todolist = new ArrayList<Todo>();

// private List<Accomodation> accomodations = new ArrayList<Accomodation>();

// private String title;
// private String startDateISOString;
// private String endDateISOString;

// }
