package com.matchalab.trip_todo_api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
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

    private Trip trip = new Trip(0L,
            "Vaundy 보러 가는 도쿠시마 여행",
            "2025-02-20T00:00:00.001Z",
            "2025-02-25T00:00:00.001Z",
            null,
            null,
            null);;

    private List<Destination> destination = new ArrayList<Destination>(
            Arrays.asList(new Destination[] { new Destination(), new Destination() }));
    private List<Todo> todolist = new ArrayList<Todo>(Arrays.asList(new Todo[] {}));
    private List<Accomodation> accomodation = new ArrayList<Accomodation>(
            Arrays.asList(new Accomodation[] {
                    new Accomodation(
                            0L,
                            trip,
                            "Hostel PAQ Tokushima",
                            "혼성 도미토리 내 베드",
                            2,
                            "PYO HYEON",
                            "2025-02-20T00:00:00.001Z",
                            "2025-02-22T00:00:00.001Z",
                            "2025-07-01T18:00:00",
                            "2025-07-01T21:00:00",
                            "2025-07-01T10:00:00",
                            "도쿠시마",
                            "dorm",
                            Map.of(
                                    "googleMap", "https://maps.app.goo.gl/81rvb62d2LKrYPNV7", "airbnb",
                                    "https://www.airbnb.co.kr/hotels/35388028?guests=1&adults=1&s=67&unique_share_id=be1c9ac3-c029-4927-a05e-efe2166f1903")),
                    new Accomodation(
                            0L,
                            trip,
                            "Yoshiko 님의 숙소",
                            "",
                            2,
                            "PYO HYEON",
                            "2025-02-23T00:00:00.001Z",
                            "2025-02-24T00:00:00.001Z",
                            "2025-07-01T17:00:00",
                            "2025-07-01T21:00:00",
                            "2025-07-01T10:00:00",
                            "나루토",
                            "airbnb",
                            Map.of(
                                    "googleMap", "https://maps.app.goo.gl/yGivrbvsiyPBDVyR8", "airbnb",
                                    "https://www.airbnb.co.kr/rooms/12317142?viralityEntryPoint=1&s=76"))
            }));

    @Bean
    TodoDTO presetTodoDTO() {
        return TodoDTO.builder()
                .id(0L)
                .order_key(0)
                .note("미리미리 할 것")
                .category("foreign")
                .type("currency")
                .title("환전")
                .iconId("💱")
                .completeDateISOString("2025-02-25T00:00:00.001Z").presetId(0L).build();
    }

    @Bean
    Todo presetTodo() {
        Todo todo = new Todo(0L,
                "미리미리 할 것",
                "2025-02-25T00:00:00.001Z",
                0,
                null,
                null,
                null);
        PresetTodoContent presetTodoContent = new PresetTodoContent(Arrays.asList(new Todo[] { todo }), 0L, "foreign",
                "currency", "환전", "💱");
        todo.setPresetTodoContent(presetTodoContent);
        return todo;
    }

    @Bean
    TodoDTO customTodoDTO() {
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
    Todo customTodo() {
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
        trip.setDestination(destination);
        trip.setTodolist(todolist);
        trip.setAccomodation(accomodation);
        return trip;
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