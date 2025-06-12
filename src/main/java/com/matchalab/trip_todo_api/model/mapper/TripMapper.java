package com.matchalab.trip_todo_api.model.mapper;

import java.util.List;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.matchalab.trip_todo_api.exception.PresetTodoContentNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.TodoContent;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.DestinationDTO;
import com.matchalab.trip_todo_api.model.DTO.PresetTodoContentDTO;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
// @RequiredArgsConstructor
public abstract class TripMapper {

    // @Autowired
    // protected TodoRepository todoRepository;

    @Autowired
    protected PresetTodoContentRepository presetTodoContentRepository;

    /*
     * mapToTodoDTO
     */

    public TodoDTO mapToTodoDTO(Todo todo) {
        TodoContent todoContent = (todo.getPresetTodoContent() != null) ? todo.getPresetTodoContent()
                : todo.getCustomTodoContent();
        return new TodoDTO(todo.getId(), todo.getOrder_key(), todo.getNote(), todo.getCompleteDateISOString(),
                (todo.getPresetTodoContent() != null) ? todo.getPresetTodoContent().getId() : null,
                todoContent.getCategory(), todoContent.getType(),
                todoContent.getTitle(), todoContent.getIconId());

    }

    /*
     * mapToTodo
     */
    @Named("mapCustomTodoContent")
    public CustomTodoContent mapCustomTodoContent(TodoDTO todoDTO) {
        return todoDTO.presetId() != null ? null
                : new CustomTodoContent(null, null, todoDTO.category(), todoDTO.type(), todoDTO.title(),
                        todoDTO.iconId());
    }

    @Named("mapPresetTodoContent")
    public PresetTodoContent mapPresetTodoContent(TodoDTO todoDTO) {
        return todoDTO.presetId() != null
                ? presetTodoContentRepository.findById(todoDTO.presetId())
                        .orElseThrow(() -> new PresetTodoContentNotFoundException(todoDTO.presetId()))
                // new PresetTodoContent(null, null, todoDTO.category(), todoDTO.type(),
                // todoDTO.title(),
                // todoDTO.iconId())
                : null;
    }

    @Mapping(target = "customTodoContent", expression = "java(mapCustomTodoContent(todoDTO))")
    @Mapping(target = "presetTodoContent", expression = "java(mapPresetTodoContent(todoDTO))")
    public abstract Todo mapToTodo(TodoDTO todoDTO);

    // @AfterMapping
    // private void afterMapping(TodoDTO todoDTO, @MappingTarget Todo todo) {
    // todo.getCustomTodoContent().setTodo(todo);
    // }

    /*
     * mapToTripDTO
     */

    @Named("mapTodolist")
    public List<TodoDTO> mapTodolist(Trip trip) {
        return trip.getTodolist().stream().map(todo -> mapToTodoDTO(todo)).toList();
    }

    @Named("mapTodolist")
    public List<Todo> mapTodolist(TripDTO tripDTO) {
        return tripDTO.todolist().stream().map(todoDTO -> mapToTodo(todoDTO)).toList();
    }

    @Named("mapAccomodation")
    public List<AccomodationDTO> mapAccomodation(Trip trip) {
        return trip.getAccomodation().stream().map(accomodation -> mapToAccomodationDTO(accomodation)).toList();
    }

    @Named("mapAccomodation")
    public List<Accomodation> mapAccomodation(TripDTO tripDTO) {
        return tripDTO.accomodation().stream().map(accomodationDTO -> {
            return mapToAccomodation(accomodationDTO);
        }).toList();
    }

    @Named("mapDestination")
    public List<DestinationDTO> mapDestination(Trip trip) {
        return trip.getDestination().stream().map(destination -> mapToDestinationDTO(destination)).toList();
    }

    @Named("mapDestination")
    public List<Destination> mapDestination(TripDTO tripDTO) {
        return tripDTO.destination().stream().map(destinationDTO -> {
            return mapToDestination(destinationDTO);
        }).toList();
    }

    @Mapping(target = "destination", expression = "java(mapDestination(trip))")
    @Mapping(target = "accomodation", expression = "java(mapAccomodation(trip))")
    @Mapping(target = "todolist", expression = "java(mapTodolist(trip))")
    public abstract TripDTO mapToTripDTO(Trip trip);

    /*
     * mapToTrip
     */
    @Mapping(target = "destination", expression = "java(mapDestination(tripDTO))")
    @Mapping(target = "accomodation", expression = "java(mapAccomodation(tripDTO))")
    @Mapping(target = "todolist", expression = "java(mapTodolist(tripDTO))")
    public abstract Trip mapToTrip(TripDTO tripDTO);

    @AfterMapping
    private void afterMapping(TodoDTO tripDTO, @MappingTarget Trip trip) {
        List<Accomodation> accomodations = trip.getAccomodation().stream().map(accomodation -> {
            accomodation.setTrip(trip);
            accomodation.setTitle("HelloWorld");
            return accomodation;
        }).toList();
        log.info("accomodations", accomodations);
        trip.setAccomodation(accomodations);
    }

    public abstract AccomodationDTO mapToAccomodationDTO(Accomodation accomodation);

    public abstract Accomodation mapToAccomodation(AccomodationDTO accomodationDTO);

    public abstract DestinationDTO mapToDestinationDTO(Destination destination);

    public abstract Destination mapToDestination(DestinationDTO destinationDTO);

    public abstract PresetTodoContentDTO mapToPresetTodoContentDTO(PresetTodoContent presetTodoContent);

    // public abstract PresetTodoContent mapToPresetTodoContent(PresetTodoContentDTO
    // presetTodoContentDTO);
}