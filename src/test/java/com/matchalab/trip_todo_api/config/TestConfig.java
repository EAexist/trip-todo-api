package com.matchalab.trip_todo_api.config;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;

import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.DestinationDTO;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;

@TestConfiguration
public class TestConfig {

    private Trip trip = new Trip(0L,
            "Vaundy 보러 가는 도쿠시마 여행",
            "2025-02-20T00:00:00.001Z",
            "2025-02-25T00:00:00.001Z",
            null,
            null,
            null);

    private List<Todo> todolist = new ArrayList<Todo>();
    private List<TodoDTO> todoDTOlist = new ArrayList<TodoDTO>();

    @Bean
    List<Destination> destinations() {
        return new ArrayList<Destination>(
                Arrays.asList(new Destination[] { new Destination(null, null, "jp", "도쿠시마", "시코쿠"),
                        new Destination(null, null, "jp", "교토", "간사이") }));
    }

    private List<DestinationDTO> destinationDTOs = new ArrayList<DestinationDTO>(
            Arrays.asList(new DestinationDTO[] { new DestinationDTO(null, "jp", "도쿠시마", "시코쿠"),
                    new DestinationDTO(null, "jp", "교토", "간사이") }));

    @Bean
    List<Accomodation> accomodations() {
        return new ArrayList<Accomodation>(
                Arrays.asList(new Accomodation[] {
                        new Accomodation(
                                null,
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
                                null,
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
    }

    private List<AccomodationDTO> accomodationDTOs = new ArrayList<AccomodationDTO>(
            Arrays.asList(new AccomodationDTO[] {
                    new AccomodationDTO(
                            null,
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
                    new AccomodationDTO(
                            null,
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
    PresetTodoContent presetTodoContent() {
        return new PresetTodoContent(Arrays.asList(new Todo[] {}), null, "foreign",
                "currency", "환전", "💱");
    }

    @Bean
    CustomTodoContent customTodoContent() {
        return new CustomTodoContent(null, null, "foreign",
                "currency", "환전", "💱");
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
        return todo;
    }

    @Bean
    TripDTO tripDTO() {
        return TripDTO.builder()
                .id(0L)
                .title("Vaundy 보러 가는 도쿠시마 여행")
                .startDateISOString("2025-02-20T00:00:00.001Z")
                .endDateISOString("2025-02-25T00:00:00.001Z")
                .destination(destinationDTOs).todolist(todoDTOlist).accomodation(accomodationDTOs).build();
    }

    @Bean
    Trip trip() {
        trip.setTodolist(todolist);
        return trip;
    }
}
