package com.matchalab.trip_todo_api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.repository.AccomodationRepository;
import com.matchalab.trip_todo_api.repository.TodoRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;
import com.matchalab.trip_todo_api.service.TripService;

@TestConfiguration
public class TestConfig {

    private List<Destination> destination = new ArrayList<Destination>(
            Arrays.asList(new Destination[] { new Destination(), new Destination() }));
    private List<Todo> todolist = new ArrayList<Todo>(Arrays.asList(new Todo[] {}));
    private List<Accomodation> accomodation = new ArrayList<Accomodation>(
            Arrays.asList(new Accomodation[] { new Accomodation(), new Accomodation() }));

    @Bean
    TodoDTO todoDTO() {
        return TodoDTO.builder()
                .id(0L)
                .order_key(0)
                .note("미리미리 할 것")
                .category("foreign")
                .type("currency")
                .title("환전")
                .iconId("💱")
                .completeDateISOString("2025-02-25T00:00:00.001Z").presetId(null).build();
    }

    @Bean
    Todo todo() {
        Todo todo = new Todo(0L,
                "미리미리 할 것",
                "2025-02-25T00:00:00.001Z",
                0,
                null,
                null,
                null);
        CustomTodoContent customTodoContent = new CustomTodoContent(todo, 0L, "foreign", "currency", "환전", "💱");
        todo.setCustomTodoContent(customTodoContent);
        return todo;
    }

    @Bean
    TripDTO tripDTO() {
        return TripDTO.builder()
                .id(0L)
                .title("Vaundy 보러 가는 도쿠시마 여행")
                .startDateISOString("2025-02-20T00:00:00.001Z")
                .endDateISOString("2025-02-25T00:00:00.001Z")
                .destination(destination).todolist(todolist).accomodation(accomodation).build();
    }

    @Bean
    Trip trip() {
        return new Trip(0L,
                "Vaundy 보러 가는 도쿠시마 여행",
                "2025-02-20T00:00:00.001Z",
                "2025-02-25T00:00:00.001Z",
                destination,
                todolist,
                accomodation);
    }
}

// TodoDTO todoDTO = TodoDTO.builder()
// .id(0L)
// .order_key(0)
// .note("미리미리 할 것")
// .category("foreign")
// .type("currency")
// .title("환전")
// .iconId("💱")
// .completeDateISOString("2025-02-25T00:00:00.001Z").isPreset(false).build();

// Todo todo = new Todo(0L,
// "미리미리 할 것",
// "2025-02-25T00:00:00.001Z",
// 0,
// null,
// null,
// null);

// List<Destination> destination = new ArrayList<Destination>(
// Arrays.asList(new Destination[] { new Destination(), new Destination() }));
// List<Todo> todolist = new ArrayList<Todo>(Arrays.asList(new Todo[] { todo,
// todo }));
// List<Accomodation> accomodation = new ArrayList<Accomodation>(
// Arrays.asList(new Accomodation[] { new Accomodation(), new Accomodation()
// }));

// TripDTO tripDTO = TripDTO.builder()
// .id(0L)
// .title("Vaundy 보러 가는 도쿠시마 여행")
// .startDateISOString("2025-02-20T00:00:00.001Z")
// .endDateISOString("2025-02-25T00:00:00.001Z")
// .destination(destination).todolist(todolist).accomodation(accomodation).build();

// Trip trip = new Trip(0L,
// "Vaundy 보러 가는 도쿠시마 여행",
// "2025-02-20T00:00:00.001Z",
// "2025-02-25T00:00:00.001Z",
// destination,
// todolist,
// accomodation);