package com.matchalab.trip_todo_api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchalab.trip_todo_api.config.TestConfig;
import com.matchalab.trip_todo_api.config.TestSecurityConfig;
import com.matchalab.trip_todo_api.exception.PresetTodoContentNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CreateTodoRequest;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;

@AutoConfigureMockMvc
@WithMockUser
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ TestConfig.class, TestSecurityConfig.class })
// @TestPropertySource(properties = { "spring.config.location =
// classpath:application-test.yml" })
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("test")
@EnableWebSecurity
public class TripControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private PresetTodoContentRepository presetTodoContentRepository;

    @Autowired
    private TripMapper tripMapper;

    // @Autowired
    // private TodoDTO todoDTO;

    // @Autowired
    // private Todo todo;

    @Autowired
    private TripDTO tripDTO;

    @Autowired
    private Trip trip;

    private Trip savedTrip;

    private ObjectMapper objectmapper = new ObjectMapper();
    // private Trip newTripDTO;

    @BeforeAll
    void setUp() {
        savedTrip = trip;
        savedTrip.setId(null);
    }

    @Test
    void trip_Given_ValidTripId_When_RequestGet_Then_CorrectTripDTO() throws Exception {

        Long id = tripRepository.save(savedTrip).getId();

        mockMvc.perform(get(String.format("/trip/%s", id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(savedTrip.getTitle()))
                .andExpect(jsonPath("startDateISOString").value(savedTrip.getStartDateISOString()))
                .andExpect(jsonPath("endDateISOString").value(savedTrip.getEndDateISOString()));
        /*
         * https://stackoverflow.com/questions/24927086/understanding-transactions-in-
         * spring-test
         */
        // .andExpect(jsonPath("destination").value(savedTrip.getDestination()))
        // .andExpect(jsonPath("todolist").value(savedTrip.getTodolist()))
        // .andExpect(jsonPath("accomodation").value(savedTrip.getAccomodation()));
    }

    @Test
    void trip_Given_TripDTO_When_RequestPost_Then_CreateNewTrip() throws Exception {

        ResultActions result = mockMvc.perform(post("/trip"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").isNotEmpty());

        Long createdTripid = objectmapper
                .readValue(result.andReturn().getResponse().getContentAsString(), TripDTO.class).id();
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s", createdTripid)));

    }

    @Test
    void updateTrip_Given_ValidIdAndNewContent_When_RequestPut_Then_UpdateTrip() throws Exception {

        Long id = tripRepository.save(new Trip()).getId();

        TripDTO newTripDTO = TripDTO.builder().title(tripDTO.title())
                .startDateISOString(tripDTO.startDateISOString()).endDateISOString(tripDTO.endDateISOString()).build();

        mockMvc.perform(put(String.format("/trip/%s", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newTripDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").value(id))
                .andExpect(jsonPath("title").value(trip.getTitle()))
                .andExpect(jsonPath("startDateISOString").value(trip.getStartDateISOString()))
                .andExpect(jsonPath("endDateISOString").value(trip.getEndDateISOString()));
        /*
         * https://stackoverflow.com/questions/24927086/understanding-transactions-in-
         * spring-test
         */
        // .andExpect(jsonPath("destination").value(trip.getDestination()))
        // .andExpect(jsonPath("todolist").value(trip.getTodolist()))
        // .andExpect(jsonPath("accomodation").value(trip.getAccomodation()));
    }

    @Test
    void createTodo_Given_ValidTripIdAndCustomTodoDTO_When_RequestPost_Then_CreateTodo() throws Exception {

        Long id = tripRepository.save(savedTrip).getId();

        ResultActions result = mockMvc.perform(post(String.format("/trip/%s/todo", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateTodoRequest("reservation", null))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .isNotEmpty());

        TodoDTO createdTodoDTO = objectmapper
                .readValue(result.andReturn().getResponse().getContentAsString(), TodoDTO.class);
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s/todo/%s", id, createdTodoDTO.id())));
    }

    @Test
    void createTodo_Given_ValidTripIdAndPresetTodoDTO_When_RequestPost_Then_CreateTodo() throws Exception {

        Long id = tripRepository.save(savedTrip).getId();
        Long presetId = 0L;
        PresetTodoContent presetTodoContent = presetTodoContentRepository.findById(presetId)
                .orElseThrow(() -> new PresetTodoContentNotFoundException(presetId));

        ResultActions result = mockMvc.perform(post(String.format("/trip/%s/todo", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateTodoRequest(null, presetId))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .isNotEmpty())
                .andExpect(jsonPath("title").value(presetTodoContent.getTitle()))
                .andExpect(jsonPath("type").value(presetTodoContent.getType()))
                .andExpect(jsonPath("iconId").value(presetTodoContent.getIconId()));

        TodoDTO createdTodoDTO = objectmapper
                .readValue(result.andReturn().getResponse().getContentAsString(), TodoDTO.class);
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s/todo/%s", id, createdTodoDTO.id())));
    }

    // @Test
    void getTodoPreset_Given_PopulatedPresetDB_When_RequestGet_Then_AllPresets() throws Exception {

        mockMvc.perform(get("/trip/todoPreset"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        /* @TODO Assert that the response body includes all presets */
    }

    @Test
    void accomodationDetail_Given_TripWithAccomodation_When_RequestGet_Then_AllAccomodations() throws Exception {

        Long id = 0L;

        mockMvc.perform(get(String.format("/trip/%s/accomodation", id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").isNotEmpty())
                .andExpect(jsonPath("$[0].title").value("Ligza"))
                .andExpect(jsonPath("$[1].title").value("Vekrir"));
    }

    /* @TODO */
    // @Test
    void createAccomodation() throws Exception {

        Long id = 0L;

        mockMvc.perform(post(String.format("/trip/%s/accomodation", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new Accomodation())))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").isNotEmpty());
    }

    protected static String asJsonString(final Object obj) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            final String jsonContent = mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
