package com.matchalab.trip_todo_api.model.mapper;

import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.DestinationDTO;
import com.matchalab.trip_todo_api.model.DTO.PresetTodoContentDTO;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.Trip;
import java.util.LinkedHashMap;
import java.util.Map;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-06-14T07:38:36+0900",
    comments = "version: 1.6.3, compiler: IncrementalProcessingEnvironment from gradle-language-java-8.14.2.jar, environment: Java 22.0.1 (Oracle Corporation)"
)
@Component
public class TripMapperImpl extends TripMapper {

    @Override
    public CustomTodoContent updateCustomTodoContentFromDto(TodoDTO todoDTO, CustomTodoContent customTodoContent) {
        if ( todoDTO == null ) {
            return customTodoContent;
        }

        if ( todoDTO.category() != null ) {
            customTodoContent.setCategory( todoDTO.category() );
        }
        if ( todoDTO.type() != null ) {
            customTodoContent.setType( todoDTO.type() );
        }
        if ( todoDTO.title() != null ) {
            customTodoContent.setTitle( todoDTO.title() );
        }
        if ( todoDTO.iconId() != null ) {
            customTodoContent.setIconId( todoDTO.iconId() );
        }
        if ( todoDTO.id() != null ) {
            customTodoContent.setId( todoDTO.id() );
        }

        return customTodoContent;
    }

    @Override
    public Todo mapToTodo(TodoDTO todoDTO) {
        if ( todoDTO == null ) {
            return null;
        }

        Todo todo = new Todo();

        todo.setId( todoDTO.id() );
        todo.setNote( todoDTO.note() );
        todo.setCompleteDateISOString( todoDTO.completeDateISOString() );
        todo.setOrderKey( todoDTO.orderKey() );

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

        tripDTO.destination( mapDestination(trip) );
        tripDTO.accomodation( mapAccomodation(trip) );
        tripDTO.todolist( mapTodolist(trip) );

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

        trip.destination( mapDestination(tripDTO) );
        trip.accomodation( mapAccomodation(tripDTO) );
        trip.todolist( mapTodolist(tripDTO) );

        return trip.build();
    }

    @Override
    public AccomodationDTO mapToAccomodationDTO(Accomodation accomodation) {
        if ( accomodation == null ) {
            return null;
        }

        AccomodationDTO.AccomodationDTOBuilder accomodationDTO = AccomodationDTO.builder();

        accomodationDTO.id( accomodation.getId() );
        accomodationDTO.title( accomodation.getTitle() );
        accomodationDTO.roomTitle( accomodation.getRoomTitle() );
        accomodationDTO.numberofClient( accomodation.getNumberofClient() );
        accomodationDTO.clientName( accomodation.getClientName() );
        accomodationDTO.checkinDateISOString( accomodation.getCheckinDateISOString() );
        accomodationDTO.checkoutDateISOString( accomodation.getCheckoutDateISOString() );
        accomodationDTO.checkinStartTimeISOString( accomodation.getCheckinStartTimeISOString() );
        accomodationDTO.checkinEndTimeISOString( accomodation.getCheckinEndTimeISOString() );
        accomodationDTO.checkoutTimeISOString( accomodation.getCheckoutTimeISOString() );
        accomodationDTO.region( accomodation.getRegion() );
        accomodationDTO.type( accomodation.getType() );
        Map<String, String> map = accomodation.getLinks();
        if ( map != null ) {
            accomodationDTO.links( new LinkedHashMap<String, String>( map ) );
        }

        return accomodationDTO.build();
    }

    @Override
    public Accomodation mapToAccomodation(AccomodationDTO accomodationDTO) {
        if ( accomodationDTO == null ) {
            return null;
        }

        Accomodation accomodation = new Accomodation();

        accomodation.setId( accomodationDTO.id() );
        accomodation.setTitle( accomodationDTO.title() );
        accomodation.setRoomTitle( accomodationDTO.roomTitle() );
        accomodation.setNumberofClient( accomodationDTO.numberofClient() );
        accomodation.setClientName( accomodationDTO.clientName() );
        accomodation.setCheckinDateISOString( accomodationDTO.checkinDateISOString() );
        accomodation.setCheckoutDateISOString( accomodationDTO.checkoutDateISOString() );
        accomodation.setCheckinStartTimeISOString( accomodationDTO.checkinStartTimeISOString() );
        accomodation.setCheckinEndTimeISOString( accomodationDTO.checkinEndTimeISOString() );
        accomodation.setCheckoutTimeISOString( accomodationDTO.checkoutTimeISOString() );
        accomodation.setRegion( accomodationDTO.region() );
        accomodation.setType( accomodationDTO.type() );
        Map<String, String> map = accomodationDTO.links();
        if ( map != null ) {
            accomodation.setLinks( new LinkedHashMap<String, String>( map ) );
        }

        return accomodation;
    }

    @Override
    public DestinationDTO mapToDestinationDTO(Destination destination) {
        if ( destination == null ) {
            return null;
        }

        DestinationDTO.DestinationDTOBuilder destinationDTO = DestinationDTO.builder();

        destinationDTO.id( destination.getId() );
        destinationDTO.nation( destination.getNation() );
        destinationDTO.title( destination.getTitle() );
        destinationDTO.region( destination.getRegion() );

        return destinationDTO.build();
    }

    @Override
    public Destination mapToDestination(DestinationDTO destinationDTO) {
        if ( destinationDTO == null ) {
            return null;
        }

        Destination destination = new Destination();

        destination.setId( destinationDTO.id() );
        destination.setNation( destinationDTO.nation() );
        destination.setTitle( destinationDTO.title() );
        destination.setRegion( destinationDTO.region() );

        return destination;
    }

    @Override
    public PresetTodoContentDTO mapToPresetTodoContentDTO(PresetTodoContent presetTodoContent) {
        if ( presetTodoContent == null ) {
            return null;
        }

        PresetTodoContentDTO.PresetTodoContentDTOBuilder presetTodoContentDTO = PresetTodoContentDTO.builder();

        presetTodoContentDTO.id( presetTodoContent.getId() );
        presetTodoContentDTO.category( presetTodoContent.getCategory() );
        presetTodoContentDTO.type( presetTodoContent.getType() );
        presetTodoContentDTO.title( presetTodoContent.getTitle() );
        presetTodoContentDTO.iconId( presetTodoContent.getIconId() );

        return presetTodoContentDTO.build();
    }

    @Override
    public Trip updateTripFromDto(TripDTO tripDTO, Trip trip) {
        if ( tripDTO == null ) {
            return trip;
        }

        if ( tripDTO.id() != null ) {
            trip.setId( tripDTO.id() );
        }
        if ( tripDTO.title() != null ) {
            trip.setTitle( tripDTO.title() );
        }
        if ( tripDTO.startDateISOString() != null ) {
            trip.setStartDateISOString( tripDTO.startDateISOString() );
        }
        if ( tripDTO.endDateISOString() != null ) {
            trip.setEndDateISOString( tripDTO.endDateISOString() );
        }

        trip.setDestination( mapDestination(tripDTO, trip) );
        trip.setAccomodation( mapAccomodation(tripDTO, trip) );
        trip.setTodolist( mapTodolist(tripDTO, trip) );

        return trip;
    }

    @Override
    public TodoDTO updateTodoDTOFromDto(TodoDTO todoDTOSource, TodoDTO todoDTOTarget) {
        if ( todoDTOSource == null ) {
            return todoDTOTarget;
        }

        return todoDTOTarget;
    }

    @Override
    public Todo updateTodoFromDto(TodoDTO todoDTO, Todo todo) {
        if ( todoDTO == null ) {
            return todo;
        }

        if ( todoDTO.id() != null ) {
            todo.setId( todoDTO.id() );
        }
        if ( todoDTO.note() != null ) {
            todo.setNote( todoDTO.note() );
        }
        if ( todoDTO.completeDateISOString() != null ) {
            todo.setCompleteDateISOString( todoDTO.completeDateISOString() );
        }
        todo.setOrderKey( todoDTO.orderKey() );

        todo.setCustomTodoContent( updateCustomTodoContentFromDto(todoDTO, todo.getCustomTodoContent()) );

        return todo;
    }

    @Override
    public Accomodation updateAccomodationFromDto(AccomodationDTO accomodationDTO, Accomodation accomodation) {
        if ( accomodationDTO == null ) {
            return accomodation;
        }

        if ( accomodationDTO.id() != null ) {
            accomodation.setId( accomodationDTO.id() );
        }
        if ( accomodationDTO.title() != null ) {
            accomodation.setTitle( accomodationDTO.title() );
        }
        if ( accomodationDTO.roomTitle() != null ) {
            accomodation.setRoomTitle( accomodationDTO.roomTitle() );
        }
        accomodation.setNumberofClient( accomodationDTO.numberofClient() );
        if ( accomodationDTO.clientName() != null ) {
            accomodation.setClientName( accomodationDTO.clientName() );
        }
        if ( accomodationDTO.checkinDateISOString() != null ) {
            accomodation.setCheckinDateISOString( accomodationDTO.checkinDateISOString() );
        }
        if ( accomodationDTO.checkoutDateISOString() != null ) {
            accomodation.setCheckoutDateISOString( accomodationDTO.checkoutDateISOString() );
        }
        if ( accomodationDTO.checkinStartTimeISOString() != null ) {
            accomodation.setCheckinStartTimeISOString( accomodationDTO.checkinStartTimeISOString() );
        }
        if ( accomodationDTO.checkinEndTimeISOString() != null ) {
            accomodation.setCheckinEndTimeISOString( accomodationDTO.checkinEndTimeISOString() );
        }
        if ( accomodationDTO.checkoutTimeISOString() != null ) {
            accomodation.setCheckoutTimeISOString( accomodationDTO.checkoutTimeISOString() );
        }
        if ( accomodationDTO.region() != null ) {
            accomodation.setRegion( accomodationDTO.region() );
        }
        if ( accomodationDTO.type() != null ) {
            accomodation.setType( accomodationDTO.type() );
        }
        if ( accomodation.getLinks() != null ) {
            Map<String, String> map = accomodationDTO.links();
            if ( map != null ) {
                accomodation.getLinks().clear();
                accomodation.getLinks().putAll( map );
            }
        }
        else {
            Map<String, String> map = accomodationDTO.links();
            if ( map != null ) {
                accomodation.setLinks( new LinkedHashMap<String, String>( map ) );
            }
        }

        return accomodation;
    }
}
