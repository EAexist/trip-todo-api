// package com.matchalab.trip_todo_api.service;

// import static org.junit.jupiter.api.Assertions.assertEquals;
// import static org.junit.jupiter.api.Assertions.assertNotNull;
// import static org.mockito.ArgumentMatchers.any;
// import static org.mockito.ArgumentMatchers.anyLong;
// import static org.mockito.Mockito.when;

// import java.util.Optional;

// import org.junit.jupiter.api.BeforeAll;
// import org.junit.jupiter.api.BeforeEach;
// import org.junit.jupiter.api.Test;
// import org.junit.jupiter.api.TestInstance;
// import org.junit.jupiter.api.TestInstance.Lifecycle;
// import org.junit.jupiter.api.extension.ExtendWith;
// import org.mapstruct.factory.Mappers;
// import org.mockito.InjectMocks;
// import org.mockito.Mock;
// import org.mockito.junit.jupiter.MockitoExtension;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.context.annotation.Import;
// import org.springframework.test.context.ContextConfiguration;
// import org.springframework.test.context.bean.override.mockito.MockitoBean;
// import org.springframework.test.context.junit.jupiter.SpringExtension;

// import com.matchalab.trip_todo_api.config.TestConfig;
// import com.matchalab.trip_todo_api.config.TripConfig;
// import com.matchalab.trip_todo_api.model.Todo;
// import com.matchalab.trip_todo_api.model.Trip;
// import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
// import com.matchalab.trip_todo_api.model.DTO.TripDTO;
// import com.matchalab.trip_todo_api.model.mapper.TripMapper;
// import com.matchalab.trip_todo_api.model.mapper.TripMapperImpl;
// import com.matchalab.trip_todo_api.repository.AccomodationRepository;
// import com.matchalab.trip_todo_api.repository.TodoRepository;
// import com.matchalab.trip_todo_api.repository.TripRepository;

// // @ExtendWith(MockitoExtension.class)
// // // @ExtendWith(SpringExtension.class)
// // // @Import({ TestConfig.class })
// @TestInstance(Lifecycle.PER_CLASS)
// @ContextConfiguration(classes = {
// TripMapperImpl.class, TestConfig.class
// })
// @SpringBootTest
// public class TripServiceTest {

// // private TodoDTO todoDTO;
// // private Todo todo;
// // private TripDTO tripDTO;

// @Autowired
// private Trip trip;

// @Autowired
// private Todo todo;

// @Autowired
// private TripMapper tripMapper;

// @MockitoBean
// private TripRepository tripRepository;

// @MockitoBean
// private TodoRepository todoRepository;

// @MockitoBean
// private AccomodationRepository accomodationRepository;

// @Autowired
// private TripService tripService;

// private Trip tripCreated = new Trip();

// @BeforeEach
// public void setUp() {
// tripService = new TripService(tripRepository, todoRepository,
// accomodationRepository, tripMapper);
// tripCreated.setId(1L);
// // when(tripRepository.findById(0L)).thenReturn(Optional.of(tripCreated));
// // when(tripRepository.save(any(Trip.class))).thenReturn(tripCreated);
// // when(accomodationRepository.get()).thenReturn(trip);
// }

// @Test
// void testGetTrip() {
// when(tripRepository.findById(0L)).thenReturn(Optional.of(trip));

// TripDTO tripDTO = tripService.getTrip(0L);
// assertNotNull(tripDTO);
// }

// @Test
// void testPutTrip() {
// when(tripRepository.save(any(Trip.class))).thenReturn(trip);

// TripDTO tripDTOPut = tripService.putTrip(trip);
// assertNotNull(tripDTOPut);
// assertEquals(trip.getTitle(), tripDTOPut.title());
// }

// @Test
// void testCreateTrip() {
// when(tripRepository.save(any(Trip.class))).thenReturn(tripCreated);

// TripDTO tripDTO = tripService.createTrip();
// assertNotNull(tripDTO);
// assertNotNull(tripDTO.id());
// }

// @Test
// void testCreateTodo() {
// when(tripRepository.findById(anyLong())).thenReturn(Optional.of(trip));
// when(todoRepository.save(any(Todo.class))).thenReturn(todo);

// TodoDTO todoDTOCreated = tripService.createTodo(0L);
// assertNotNull(todoDTOCreated);
// assertNotNull(todoDTOCreated.id());
// }

// // @Test
// // void testGetAccomodation() {
// // List<Accomodation> accomodation = tripService.getAccomodation(0L);
// // assertNotNull(accomodation);
// // }

// // @Test
// // void testCreateAccomodation() {
// // Accomodation accomodation = tripService.createAccomodation(new
// // Accomodation());
// // assertNotNull(accomodation);
// // assertNotNull(accomodation.getId());
// // }
// }
