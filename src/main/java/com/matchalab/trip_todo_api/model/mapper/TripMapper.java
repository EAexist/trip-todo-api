package com.matchalab.trip_todo_api.model.mapper;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;

import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;
import org.springframework.beans.factory.annotation.Autowired;

import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.TodoContent;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.repository.AccomodationRepository;
import com.matchalab.trip_todo_api.repository.TodoRepository;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class TripMapper {

    @Autowired
    protected TodoRepository todoRepository;

    @Autowired
    protected AccomodationRepository accomodationRepository;

    /*
     * mapToTodoDTO
     */
    public TodoDTO mapToTodoDTO(Todo todo) {
        TodoContent todoContent = todo.getPresetTodoContent() != null ? todo.getPresetTodoContent()
                : todo.getCustomTodoContent();
        return new TodoDTO(todo.getId(), todo.getOrder_key(), todo.getNote(), todo.getCompleteDateISOString(),
                todo.getPresetTodoContent() != null, todoContent.getCategory(), todoContent.getType(),
                todoContent.getTitle(), todoContent.getIconId());

    }

    /*
     * mapToTodo
     */

    @Named("mapCustomTodoContent")
    public CustomTodoContent mapCustomTodoContent(TodoDTO todoDTO) {
        return todoDTO.isPreset() ? null
                : new CustomTodoContent(null, null, todoDTO.category(), todoDTO.type(), todoDTO.title(),
                        todoDTO.iconId());
    }

    @Named("mapPresetTodoContent")
    public PresetTodoContent mapPresetTodoContent(TodoDTO todoDTO) {
        return todoDTO.isPreset()
                ? new PresetTodoContent(null, null, todoDTO.category(), todoDTO.type(), todoDTO.title(),
                        todoDTO.iconId())
                : null;
    }

    @Mapping(target = "customTodoContent", expression = "java(mapCustomTodoContent(todoDTO))")
    @Mapping(target = "presetTodoContent", expression = "java(mapPresetTodoContent(todoDTO))")
    public abstract Todo mapToTodo(TodoDTO todoDTO);

    @AfterMapping
    private void afterMapping(TodoDTO todoDTO, @MappingTarget Todo todo) {
        todo.getCustomTodoContent().setTodo(todo);
        todo.getPresetTodoContent().setTodo(todo);
    }

    /*
     * mapToTripDTO
     */

    // @Mapping
    public abstract TripDTO mapToTripDTO(Trip trip);

    /*
     * mapToTrip
     */
    // @Mapping
    public abstract Trip mapToTrip(TripDTO tripDTO);
}