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

@ExtendWith(SpringExtension.class)
@Import({ TestConfig.class })
@ContextConfiguration(classes = {
        TripMapperImpl.class
})
public class TripMapperTest {

    @Autowired
    private TodoDTO todoDTO;

    @Autowired
    private Todo todo;

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
        CustomTodoContent customTodoContent = new CustomTodoContent(todo, 0L, "foreign", "currency", "환전", "💱");
        todo.setCustomTodoContent(customTodoContent);
    }

    @Test
    void testMapToTodo() {
        // when(presetTodoContentRepository.findById(0L)).thenReturn(Optional.of(tripCreated));

        Todo mappedTodo = tripMapper.mapToTodo(todoDTO);
        assertNotNull(todoDTO);
        assertNotNull(mappedTodo);
        assertEquals(todo.getNote(), mappedTodo.getNote());
        assertEquals(todo.getCompleteDateISOString(), mappedTodo.getCompleteDateISOString());
        assertEquals(todo.getOrder_key(), mappedTodo.getOrder_key());
        assertEquals(todo.getTrip(), mappedTodo.getTrip());

        TodoContent todoContent = todo.getPresetTodoContent() != null ? todo.getPresetTodoContent()
                : todo.getCustomTodoContent();
        TodoContent mappedTodoContent = mappedTodo.getPresetTodoContent() != null ? mappedTodo.getPresetTodoContent()
                : mappedTodo.getCustomTodoContent();
        assertEquals(todoContent.getCategory(), mappedTodoContent.getCategory());
        assertEquals(todoContent.getClass(), mappedTodoContent.getClass());
        assertEquals(todoContent.getIconId(), mappedTodoContent.getIconId());
        assertEquals(todoContent.getId(), mappedTodoContent.getId());
        assertEquals(todoContent.getTitle(), mappedTodoContent.getTitle());
        assertEquals(todoContent.getType(), mappedTodoContent.getType());
        // assertEquals(todoContent.getTodo(), mappedTodo.getCustomTodoContent());
    }

    @Test
    void testMapToTodoDTO() {
        TodoDTO mappedTodoDTO = tripMapper.mapToTodoDTO(todo);
        assertNotNull(todo);
        assertNotNull(mappedTodoDTO);
        assertEquals(todoDTO.id(), mappedTodoDTO.id());
        assertEquals(todoDTO.order_key(), mappedTodoDTO.order_key());
        assertEquals(todoDTO.note(), mappedTodoDTO.note());
        assertEquals(todoDTO.category(), mappedTodoDTO.category());
        assertEquals(todoDTO.type(), mappedTodoDTO.type());
        assertEquals(todoDTO.title(), mappedTodoDTO.title());
        assertEquals(todoDTO.iconId(), mappedTodoDTO.iconId());
        assertEquals(todoDTO.completeDateISOString(), mappedTodoDTO.completeDateISOString());
    }

    @Test
    void testMapToTrip() {
        Trip mappedTrip = tripMapper.mapToTrip(tripDTO);
        assertEquals(trip.getTitle(), mappedTrip.getTitle());
        assertEquals(trip.getStartDateISOString(), mappedTrip.getStartDateISOString());
        assertEquals(trip.getEndDateISOString(), mappedTrip.getEndDateISOString());
        assertEquals(trip.getDestination().size(), mappedTrip.getDestination().size());
        for (int i = 0; i < trip.getDestination().size(); i++) {
            assertEquals(trip.getDestination().get(i).getId(), mappedTrip.getDestination().get(i).getId());
            assertEquals(trip.getDestination().get(i).getTitle(), mappedTrip.getDestination().get(i).getTitle());
        }
        for (int i = 0; i < trip.getTodolist().size(); i++) {
            assertEquals(trip.getTodolist().get(i).getId(), mappedTrip.getTodolist().get(i).getId());
            assertEquals(trip.getTodolist().get(i).getCompleteDateISOString(),
                    mappedTrip.getTodolist().get(i).getCompleteDateISOString());
        }
        for (int i = 0; i < trip.getAccomodation().size(); i++) {
            assertEquals(trip.getAccomodation().get(i).getId(), mappedTrip.getAccomodation().get(i).getId());
            assertEquals(trip.getAccomodation().get(i).getTitle(), mappedTrip.getAccomodation().get(i).getTitle());
        }
    }

    @Test
    void testMapToTripDTO() {
        TripDTO mappedTripDTO = tripMapper.mapToTripDTO(trip);
        assertEquals(tripDTO.title(), mappedTripDTO.title());
        assertEquals(tripDTO.startDateISOString(), mappedTripDTO.startDateISOString());
        assertEquals(tripDTO.endDateISOString(), mappedTripDTO.endDateISOString());
        for (int i = 0; i < tripDTO.destination().size(); i++) {
            assertEquals(tripDTO.destination().get(i).getId(), mappedTripDTO.destination().get(i).getId());
            assertEquals(tripDTO.destination().get(i).getTitle(), mappedTripDTO.destination().get(i).getTitle());
        }
        for (int i = 0; i < tripDTO.todolist().size(); i++) {
            assertEquals(tripDTO.todolist().get(i).getId(), mappedTripDTO.todolist().get(i).getId());
            assertEquals(tripDTO.todolist().get(i).getCompleteDateISOString(),
                    mappedTripDTO.todolist().get(i).getCompleteDateISOString());
        }
        for (int i = 0; i < tripDTO.accomodation().size(); i++) {
            assertEquals(tripDTO.accomodation().get(i).getId(), mappedTripDTO.accomodation().get(i).getId());
            assertEquals(tripDTO.accomodation().get(i).getTitle(), mappedTripDTO.accomodation().get(i).getTitle());
        }

    }
}
