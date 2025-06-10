package com.matchalab.trip_todo_api.model.mapper;

import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.Trip;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-10T18:18:32+0900",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.2.jar, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class TripMapperImpl extends TripMapper {

    @Override
    public Todo mapToTodo(TodoDTO todoDTO) {
        if ( todoDTO == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setId( todoDTO.id() );
        todo.setNote( todoDTO.note() );
        todo.setCompleteDateISOString( todoDTO.completeDateISOString() );
        todo.setOrder_key( todoDTO.order_key() );

        todo.setCustomTodoContent( mapCustomTodoContent(todoDTO) );
        todo.setPresetTodoContent( mapPresetTodoContent(todoDTO) );

        return todo;
    }

    @Override
    public TripDTO mapToTripDTO(Trip trip) {
        if ( trip == null ) {
            return null;
        }

        TripDTO.TripDTOBuilder tripDTO = TripDTO.builder();

        tripDTO.id( trip.getId() );
        tripDTO.title( trip.getTitle() );
        tripDTO.startDateISOString( trip.getStartDateISOString() );
        tripDTO.endDateISOString( trip.getEndDateISOString() );
        List<Destination> list = trip.getDestination();
        if ( list != null ) {
            tripDTO.destination( new ArrayList<Destination>( list ) );
        }
        List<Todo> list1 = trip.getTodolist();
        if ( list1 != null ) {
            tripDTO.todolist( new ArrayList<Todo>( list1 ) );
        }
        List<Accomodation> list2 = trip.getAccomodation();
        if ( list2 != null ) {
            tripDTO.accomodation( new ArrayList<Accomodation>( list2 ) );
        }

        return tripDTO.build();
    }

    @Override
    public Trip mapToTrip(TripDTO tripDTO) {
        if ( tripDTO == null ) {
            return null;
        }

        Trip.TripBuilder trip = Trip.builder();

        trip.id( tripDTO.id() );
        trip.title( tripDTO.title() );
        trip.startDateISOString( tripDTO.startDateISOString() );
        trip.endDateISOString( tripDTO.endDateISOString() );
        List<Destination> list = tripDTO.destination();
        if ( list != null ) {
            trip.destination( new ArrayList<Destination>( list ) );
        }
        List<Todo> list1 = tripDTO.todolist();
        if ( list1 != null ) {
            trip.todolist( new ArrayList<Todo>( list1 ) );
        }
        List<Accomodation> list2 = tripDTO.accomodation();
        if ( list2 != null ) {
            trip.accomodation( new ArrayList<Accomodation>( list2 ) );
        }

        return trip.build();
    }
}
