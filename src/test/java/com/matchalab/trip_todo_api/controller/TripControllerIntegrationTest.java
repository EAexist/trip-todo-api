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

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchalab.trip_todo_api.DataLoader;
import com.matchalab.trip_todo_api.config.TestConfig;
import com.matchalab.trip_todo_api.config.TestSecurityConfig;
import com.matchalab.trip_todo_api.exception.PresetTodoContentNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CreateTodoRequest;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.repository.AccomodationRepository;
import com.matchalab.trip_todo_api.repository.DestinationRepository;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    private AccomodationRepository accomodationRepository;

    @Autowired
    private DestinationRepository destinationRepository;

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

    @Autowired
    private List<Accomodation> accomodations;

    @Autowired
    private List<Destination> destinations;

    private Trip savedTrip;

    @BeforeEach
    void setUp() {
        tripRepository.deleteAll();
        destinationRepository.deleteAll();
        accomodationRepository.deleteAll();
        savedTrip = tripRepository
                .save(new Trip(trip));

        List<Destination> savedDests = destinationRepository.saveAll(destinations.stream()
                .map(dest -> {
                    Destination newDest = new Destination(dest);
                    newDest.setTrip(savedTrip);
                    return newDest;
                })
                .toList());
        savedTrip.setDestination(savedDests);

        List<Accomodation> savedAccs = accomodationRepository.saveAll(accomodations.stream()
                .map(acc -> {
                    Accomodation newAcc = new Accomodation(acc);
                    newAcc.setTrip(savedTrip);
                    return newAcc;
                })
                .toList());
        savedTrip.setAccomodation(savedAccs);
    }

    @Test
    void trip_Given_ValidTripId_When_RequestGet_Then_CorrectTripDTO() throws Exception {

        Long id = savedTrip.getId();

        ResultActions result = mockMvc.perform(get(String.format("/trip/%s", id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        TripDTO responseTripDTO = asObject(result, TripDTO.class);
        assertThat(responseTripDTO).usingRecursiveComparison()
                .ignoringFieldsOfTypes(TripDTO.class).ignoringFields("*.id")
                .isEqualTo(tripMapper.mapToTripDTO(savedTrip));
        /*
         * https://stackoverflow.com/questions/24927086/understanding-transactions-in-
         * spring-test
         */
    }

    @Test
    void createTrip_When_RequestPost_Then_CreateNewTrip() throws Exception {

        ResultActions result = mockMvc.perform(post("/trip"))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").isNotEmpty());

        Long createdTripid = asObject(result, TripDTO.class).id();
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s", createdTripid)));

    }

    @Test
    void updateTrip_Given_ValidIdAndNewContent_When_RequestPut_Then_UpdateTrip() throws Exception {

        Long id = tripRepository.save(new Trip()).getId();

        TripDTO newTripDTO = TripDTO.builder().title(tripDTO.title())
                .startDateISOString(tripDTO.startDateISOString()).endDateISOString(tripDTO.endDateISOString())
                .accomodation(tripDTO.accomodation()).destination(tripDTO.destination()).todolist(tripDTO.todolist())
                .build();

        ResultActions result = mockMvc.perform(put(String.format("/trip/%s", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(newTripDTO)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        Trip responseTrip = asObject(result, Trip.class);
        assertThat(responseTrip).usingRecursiveComparison()
                .ignoringFieldsOfTypes(Trip.class, Destination.class, Accomodation.class).ignoringFields("id")
                .isEqualTo(trip);

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

        Long id = savedTrip.getId();

        ResultActions result = mockMvc.perform(post(String.format("/trip/%s/todo", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateTodoRequest("reservation", null))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .isNotEmpty());

        TodoDTO createdTodoDTO = asObject(result, TodoDTO.class);
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s/todo/%s", id, createdTodoDTO.id())));
    }

    @Test
    void createTodo_Given_ValidTripIdAndPresetTodoDTO_When_RequestPost_Then_CreateTodo() throws Exception {

        Long id = savedTrip.getId();
        Long presetId = 1L;
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

        TodoDTO createdTodoDTO = asObject(result, TodoDTO.class);
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s/todo/%s", id, createdTodoDTO.id())));
    }

    /* @TODO */
    // @Test
    void deleteTodo_When_Then() throws Exception {
    }

    @Test
    void getTodoPreset_Given_PopulatedPresetDB_When_RequestGet_Then_AllPresets() throws Exception {

        log.info("[getTodoPreset_Given_PopulatedPresetDB_When_RequestGet_Then_AllPresets] HELLO");

        ResultActions result = mockMvc.perform(get("/trip/0/todoPreset"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<PresetTodoContent> actualPresetTodoContent = asObject(result,
                new TypeReference<List<PresetTodoContent>>() {
                });

        List<PresetTodoContent> expectedPresetTodoContent = DataLoader.readPresetJson();

        assertThat(actualPresetTodoContent).usingRecursiveComparison()
                .ignoringFieldsOfTypes(PresetTodoContent.class).ignoringFields()
                .isEqualTo(expectedPresetTodoContent);
    }

    @Test
    void accomodationPlan_Given_TripWithAccomodation_When_RequestGet_Then_AllAccomodations() throws Exception {

        Long id = savedTrip.getId();

        ResultActions result = mockMvc.perform(get(String.format("/trip/%s/accomodation", id)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].id").isNotEmpty())
                .andExpect(jsonPath("$[1].id").isNotEmpty());

        List<AccomodationDTO> actualAccomodationDTOs = asObject(result, new TypeReference<List<AccomodationDTO>>() {
        });

        List<AccomodationDTO> expectedDTO = accomodations.stream().map(tripMapper::mapToAccomodationDTO).toList();

        assertThat(actualAccomodationDTOs).usingRecursiveComparison()
                .ignoringFieldsOfTypes(AccomodationDTO.class).ignoringFields(".*id")
                .isEqualTo(expectedDTO);

    }

    @Test
    void createAccomodation_When_RequestPost_Then_CreateNewAccomodation() throws Exception {

        Long id = savedTrip.getId();

        ResultActions result = mockMvc.perform(post(String.format("/trip/%s/accomodation", id))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id").isNotEmpty());

        Accomodation createdAccomodation = asObject(result, Accomodation.class);
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s/accomodation/%s", id, createdAccomodation.getId())));
    }

    /* @TODO */
    // @Test
    void deleteAccomodation_When_Then() throws Exception {
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

    protected static <T> T asObject(final ResultActions result, TypeReference<T> classType) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(result.andReturn().getResponse().getContentAsString(),
                    classType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected static <T> T asObject(final ResultActions result, Class<T> classType) {
        try {
            final ObjectMapper mapper = new ObjectMapper();
            return mapper.readValue(result.andReturn().getResponse().getContentAsString(),
                    classType);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
