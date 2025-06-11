package com.matchalab.trip_todo_api.model.mapper;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.matchalab.trip_todo_api.config.TestConfig;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.TodoContent;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;
import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@Import({ TestConfig.class })
@ContextConfiguration(classes = {
        TripMapperImpl.class
})
public class TripMapperTest {

    @Autowired
    private TodoDTO customTodoDTO;

    @Autowired
    private Todo customTodo;

    @Autowired
    private TripDTO tripDTO;

    @Autowired
    private Trip trip;
    /*
     * https://velog.io/@gwichanlee/MapStruct-Test-Code-%EC%9E%91%EC%84%B1
     * https://www.baeldung.com/mapstruct
     */
    @Autowired
    private TripMapper tripMapper;
    // private final TripMapper tripMapper = Mappers.getMapper(TripMapper.class);

    @MockitoBean
    private PresetTodoContentRepository presetTodoContentRepository;

    @BeforeEach
    public void setUp() throws Exception {
        CustomTodoContent customTodoContent = new CustomTodoContent(customTodo, 0L, "foreign", "currency", "환전", "💱");
        customTodo.setCustomTodoContent(customTodoContent);
    }

    @Test
    void mapToTodo_Given_customToDoDTO_When_mapped_Then_correctTodo() {

        Todo mappedTodo = tripMapper.mapToTodo(customTodoDTO);
        assertNotNull(customTodoDTO);
        assertNotNull(mappedTodo);
        assertThat(customTodo).usingRecursiveComparison().isEqualTo(mappedTodo).ignoringFields("customTodoContent");

        TodoContent todoContent = customTodo.getPresetTodoContent() != null ? customTodo.getPresetTodoContent()
                : customTodo.getCustomTodoContent();
        TodoContent mappedTodoContent = mappedTodo.getPresetTodoContent() != null ? mappedTodo.getPresetTodoContent()
                : mappedTodo.getCustomTodoContent();
        assertThat(todoContent).usingRecursiveComparison().isEqualTo(mappedTodoContent);
    }

    @Test
    void mapToTodoDTO_Given_customToDo_When_mapped_Then_correctTodoDTO() {
        TodoDTO mappedTodoDTO = tripMapper.mapToTodoDTO(customTodo);
        assertNotNull(customTodo);
        assertNotNull(mappedTodoDTO);
        assertThat(customTodoDTO).usingRecursiveComparison().isEqualTo(mappedTodoDTO);
    }

    @Test
    void mapToTripDTO_Given_tripDTO_When_mapped_Then_correctTrip() {
        Trip mappedTrip = tripMapper.mapToTrip(tripDTO);
        assertThat(trip).usingRecursiveComparison().isEqualTo(mappedTrip).ignoringFields();
        // for (int i = 0; i < trip.getDestination().size(); i++) {
        // assertEquals(trip.getDestination().get(i).getId(),
        // mappedTrip.getDestination().get(i).getId());
        // assertEquals(trip.getDestination().get(i).getTitle(),
        // mappedTrip.getDestination().get(i).getTitle());
        // }
        // for (int i = 0; i < trip.getTodolist().size(); i++) {
        // assertEquals(trip.getTodolist().get(i).getId(),
        // mappedTrip.getTodolist().get(i).getId());
        // assertEquals(trip.getTodolist().get(i).getCompleteDateISOString(),
        // mappedTrip.getTodolist().get(i).getCompleteDateISOString());
        // }
        // for (int i = 0; i < trip.getAccomodation().size(); i++) {
        // assertEquals(trip.getAccomodation().get(i).getId(),
        // mappedTrip.getAccomodation().get(i).getId());
        // assertEquals(trip.getAccomodation().get(i).getTitle(),
        // mappedTrip.getAccomodation().get(i).getTitle());
        // }
    }

    @Test
    void mapToTripDTO_Given_trip_When_mapped_Then_correctTripDTO() {
        TripDTO mappedTripDTO = tripMapper.mapToTripDTO(trip);
        assertThat(tripDTO).usingRecursiveComparison().isEqualTo(mappedTripDTO).ignoringFields();
        // for (int i = 0; i < tripDTO.destination().size(); i++) {
        // assertEquals(tripDTO.destination().get(i).getId(),
        // mappedTripDTO.destination().get(i).getId());
        // assertEquals(tripDTO.destination().get(i).getTitle(),
        // mappedTripDTO.destination().get(i).getTitle());
        // }
        // for (int i = 0; i < tripDTO.todolist().size(); i++) {
        // assertEquals(tripDTO.todolist().get(i).getId(),
        // mappedTripDTO.todolist().get(i).getId());
        // assertEquals(tripDTO.todolist().get(i).getCompleteDateISOString(),
        // mappedTripDTO.todolist().get(i).getCompleteDateISOString());
        // }
        // for (int i = 0; i < tripDTO.accomodation().size(); i++) {
        // assertEquals(tripDTO.accomodation().get(i).getId(),
        // mappedTripDTO.accomodation().get(i).getId());
        // assertEquals(tripDTO.accomodation().get(i).getTitle(),
        // mappedTripDTO.accomodation().get(i).getTitle());
        // }

    }
}
