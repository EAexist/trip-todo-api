package com.matchalab.trip_todo_api.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchalab.trip_todo_api.DataLoader;
import com.matchalab.trip_todo_api.config.TestConfig;
import com.matchalab.trip_todo_api.exception.NotFoundException;
import com.matchalab.trip_todo_api.exception.PresetTodoContentNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.Icon;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.TodoContent;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.PresetDTO;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.model.request.CreateTodoRequest;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;
import com.matchalab.trip_todo_api.repository.TodoRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@AutoConfigureMockMvc
@WithMockUser
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Import({ TestConfig.class })
// @TestPropertySource(properties = { "spring.config.location =
// classpath:application-test.yml" })
@TestInstance(Lifecycle.PER_CLASS)
@ActiveProfiles("dev")
@EnableWebSecurity
public class TripControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TripRepository tripRepository;

    @Autowired
    private TodoRepository todoRepository;

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

    @Autowired
    private Accomodation[] accomodations;

    @Autowired
    private Destination[] destinations;

    @Autowired
    private CustomTodoContent customTodoContent;

    @Autowired
    private PresetTodoContent presetTodoContent;

    @Autowired
    private Todo presetTodo;

    @Autowired
    private Todo customTodo;

    @Autowired
    private TodoDTO customTodoDTO;

    private Trip savedTrip;

    @BeforeEach
    void setUp() {
        // presetTodoContentRepository.deleteAll();
        // customTodoContentRepository.deleteAll();
        // todoRepository.deleteAll();
        // tripRepository.deleteAll();
        // destinationRepository.deleteAll();
        // accomodationRepository.deleteAll();

        savedTrip = new Trip();
        savedTrip.setDestination(Arrays.stream(destinations)
                .map(dest -> {
                    Destination newDest = new Destination(dest);
                    newDest.setTrip(savedTrip);
                    return newDest;
                })
                .toList());
        savedTrip.setAccomodation(Arrays.stream(accomodations)
                .map(acc -> {
                    Accomodation newAcc = new Accomodation(acc);
                    newAcc.setTrip(savedTrip);
                    return newAcc;
                })
                .toList());

        final record TodoSet<T extends TodoContent>(
                Todo todo,
                T content) {
        }
        ;

        List<Todo> savedTodos = Arrays
                .stream(new TodoSet[] { new TodoSet<PresetTodoContent>(presetTodo, presetTodoContent),
                        new TodoSet<CustomTodoContent>(customTodo, customTodoContent)
                }).map(todoset -> {
                    Todo newTodo = new Todo(todoset.todo);
                    newTodo.setTrip(savedTrip);
                    if (todoset.content instanceof PresetTodoContent) {
                        newTodo.setPresetTodoContent(presetTodoContentRepository
                                .findById(((PresetTodoContent) todoset.content).getId())
                                .orElseThrow(
                                        () -> new NotFoundException(((PresetTodoContent) todoset.content).getId())));
                    } else {
                        newTodo.setCustomTodoContent(new CustomTodoContent((CustomTodoContent) todoset.content));
                    }
                    return newTodo;
                }).toList();
        savedTrip.setTodolist(savedTodos);
        tripRepository.save(savedTrip);
        log.info(String.format("[setUp] savedTrip=%s", asJsonString(tripMapper.mapToTripDTO(savedTrip))));
    }

    @Test
    void testExtractTextFromImage() throws Exception {

        Long id = savedTrip.getId();
        File _file = new ClassPathResource("/image/accomodation-agoda-app-ios_1.png").getFile();
        MultipartFile file = new MockMultipartFile(_file.getName(), new FileInputStream(_file));

        ResultActions result = mockMvc
                .perform(post(String.format("/trip/%s/reservation", id))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(asJsonString(new MultipartFile[] { file
                        })))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));
        // this.mockMvc
        // .perform(get(TEXT_IMAGE_URL))
        // .andDo(
        // response -> {
        // ModelAndView result = response.getModelAndView();
        // String textFromImage = ((String) result.getModelMap().get("text")).trim();
        // assertThat(textFromImage).isEqualTo("STOP");
        // });
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
                .ignoringFieldsOfTypes().ignoringFields("*.id")
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
    void patchTrip_Given_ValidIdAndNewContent_When_RequestPut_Then_patchTrip() throws Exception {

        TripDTO tripDTOToPatch = new TripDTO(null,
                false,
                "새 여행 이름",
                "2025-02-10T00:00:00.001Z",
                null,
                null,
                null,
                null);

        Long id = savedTrip.getId();

        ResultActions result = mockMvc.perform(patch(String.format("/trip/%s", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(tripDTOToPatch)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").value(tripDTOToPatch.title()))
                .andExpect(jsonPath("startDateISOString").value(tripDTOToPatch.startDateISOString()));

        TripDTO actualTripDTO = asObject(result, TripDTO.class);

        log.info(String.format("[patchTrip_Given_ValidIdAndNewContent_When_RequestPut_Then_patchTrip] %s",
                asJsonString(actualTripDTO)));

        log.info(String.format("[patchTrip_Given_ValidIdAndNewContent_When_RequestPut_Then_patchTrip] %s",
                asJsonString(tripMapper.mapToTripDTO(savedTrip))));

        assertThat(actualTripDTO).usingRecursiveComparison()
                // .ignoringFieldsOfTypes(TripDTO.class)
                .ignoringFields("title", "startDateISOString")
                .isEqualTo(tripMapper.mapToTripDTO(savedTrip));

        /*
         * https://stackoverflow.com/questions/24927086/understanding-transactions-in-
         * spring-test
         */
        // .andExpect(jsonPath("destination").value(trip.getDestination()))
        // .andExpect(jsonPath("todolist").value(trip.getTriplist()))
        // .andExpect(jsonPath("accomodation").value(trip.getAccomodation()));
    }

    @Test
    void createTodo_Given_ValidTripIdAndCustomTodoDTO_When_RequestPost_Then_CreateTodo() throws Exception {

        Long id = savedTrip.getId();

        ResultActions result = mockMvc.perform(post(String.format("/trip/%s/todo", id))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(new CreateTodoRequest("reservation", null, null))))
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
                .content(asJsonString(new CreateTodoRequest(null, null, presetId))))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("id")
                        .isNotEmpty())
                .andExpect(jsonPath("title").value(presetTodoContent.getTitle()))
                .andExpect(jsonPath("type").value(presetTodoContent.getType()))
                .andExpect(jsonPath("icon").value(presetTodoContent.getIcon()));

        TodoDTO createdTodoDTO = asObject(result, TodoDTO.class);
        result.andExpect(header().string("Location",
                String.format("http://localhost/trip/%s/todo/%s", id, createdTodoDTO.id())));
    }

    @Test
    void patchTodo_Given_NewContentAndOrderKey_When_RequestPatchCustomTodo_Then_UpdateTodo() throws Exception {

        Long id = savedTrip.getId();

        Todo todo = savedTrip.getTodolist().stream().filter(todo_ -> todo_.getPresetTodoContent() == null).toList()
                .getFirst();

        TodoDTO todoDTOToPatch = TodoDTO.builder().id(9L).note("새로운 노트").category("goods").title("새로운 할 일 이름")
                .icon(new Icon("🎁")).build();

        ResultActions result = mockMvc.perform(patch(String.format("/trip/%s/todo/%s", id, todo.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todoDTOToPatch)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("title").value(todoDTOToPatch.title()))
                .andExpect(jsonPath("orderKey").value(todoDTOToPatch.orderKey()))
                .andExpect(jsonPath("note").value(todoDTOToPatch.note()))
                .andExpect(jsonPath("icon").value(todoDTOToPatch.icon()));

        TodoDTO actualTodoDTO = asObject(result, TodoDTO.class);
        assertThat(actualTodoDTO).usingRecursiveComparison()
                .ignoringFieldsOfTypes()
                .ignoringFields("title", "note", "icon", "orderKey")
                .isEqualTo(tripMapper.mapToTodoDTO(todo));

        assertThat(todoRepository.findById(actualTodoDTO.id()).get().getCustomTodoContent().getId()).isEqualTo(
                todo.getCustomTodoContent().getId());
    }

    @Test
    void patchTodo_Given_NewContentAndOrderKey_When_RequestPatchPresetTodo_Then_UpdateTodo() throws Exception {

        Long id = savedTrip.getId();

        Todo todo = savedTrip.getTodolist().stream().filter(todo_ -> todo_.getPresetTodoContent() != null).toList()
                .getFirst();

        TodoDTO todoDTOToPatch = TodoDTO.builder().id(9L).note("새로운 노트")
                .completeDateISOString("2025-02-20T00:00:00.001Z").category("goods").title("새로운 할 일 이름")
                .icon(new Icon("🎁")).build();

        ResultActions result = mockMvc.perform(patch(String.format("/trip/%s/todo/%s", id, todo.getId()))
                .contentType(MediaType.APPLICATION_JSON)
                .content(asJsonString(todoDTOToPatch)))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("note").value(todoDTOToPatch.note()))
                .andExpect(jsonPath("orderKey").value(todoDTOToPatch.orderKey()))
                .andExpect(jsonPath("completeDateISOString").value(todoDTOToPatch.completeDateISOString()));

        TodoDTO actualTodoDTO = asObject(result, TodoDTO.class);
        assertThat(actualTodoDTO).usingRecursiveComparison()
                .ignoringFieldsOfTypes()
                .ignoringFields("note", "orderKey", "completeDateISOString")
                .isEqualTo(tripMapper.mapToTodoDTO(todo));

        assertThat(todoRepository.findById(actualTodoDTO.id()).get().getPresetTodoContent().getId()).isEqualTo(
                todo.getPresetTodoContent().getId());
    }

    /* @TODO */
    // @Test
    void deleteTodo_When_Then() throws Exception {
    }

    @Test
    void getTodoPreset_Given_PopulatedPresetDB_When_RequestGet_Then_AllPresets() throws Exception {

        log.info(String.format("[getTodoPreset_Given_PopulatedPresetDB_When_RequestGet_Then_AllPresets] %s",
                asJsonString(presetTodoContentRepository.findAll())));

        ResultActions result = mockMvc.perform(get("/trip/0/todoPreset"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON));

        List<PresetDTO> actualPresetDTO = asObject(result,
                new TypeReference<List<PresetDTO>>() {
                });

        List<PresetDTO> expectedPresetDTO = DataLoader.readPresetJson().stream()
                .map(presetTodoContent -> new PresetDTO(true, presetTodoContent)).toList();

        assertThat(actualPresetDTO).usingRecursiveComparison()
                .ignoringFieldsOfTypes().ignoringFields()
                .isEqualTo(expectedPresetDTO);
    }

    /* @TODO */
    // @Test
    void createDestination_When_Then() throws Exception {
    }

    /* @TODO */
    // @Test
    void deleteDestination_When_Then() throws Exception {
    }

    // @Test
    // void
    // accomodationPlan_Given_TripWithAccomodation_When_RequestGet_Then_AllAccomodations()
    // throws Exception {

    // Long id = savedTrip.getId();

    // ResultActions result =
    // mockMvc.perform(get(String.format("/trip/%s/accomodation", id)))
    // .andDo(print())
    // .andExpect(status().isOk())
    // .andExpect(content().contentType(MediaType.APPLICATION_JSON))
    // .andExpect(jsonPath("$[0].id").isNotEmpty())
    // .andExpect(jsonPath("$[1].id").isNotEmpty());

    // List<AccomodationDTO> actualAccomodationDTOs = asObject(result, new
    // TypeReference<List<AccomodationDTO>>() {
    // });

    // List<AccomodationDTO> expectedDTO =
    // Arrays.stream(accomodations).map(tripMapper::mapToAccomodationDTO).toList();

    // assertThat(actualAccomodationDTOs).usingRecursiveComparison()
    // .ignoringFieldsOfTypes().ignoringFields(".*id")
    // .isEqualTo(expectedDTO);

    // }

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
