// package com.matchalab.trip_todo_api.controller;

// import java.net.URI;
// import java.util.List;

// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.DeleteMapping;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.PatchMapping;
// import org.springframework.web.bind.annotation.RequestBody;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;
// import org.springframework.web.client.HttpClientErrorException;
// import org.springframework.web.multipart.MultipartFile;
// import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// import com.matchalab.trip_todo_api.model.Accomodation;
// import com.matchalab.trip_todo_api.model.Destination;
// import com.matchalab.trip_todo_api.model.PresetTodoContent;
// import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
// import com.matchalab.trip_todo_api.model.DTO.DestinationDTO;
// import com.matchalab.trip_todo_api.model.DTO.PresetDTO;
// import com.matchalab.trip_todo_api.model.DTO.ReservationImageAnalysisResult;
// import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
// import com.matchalab.trip_todo_api.model.DTO.TripDTO;
// import com.matchalab.trip_todo_api.model.request.CreateTodoRequest;
// import com.matchalab.trip_todo_api.service.TripService;
// import com.matchalab.trip_todo_api.utils.Utils;

// import io.micrometer.common.lang.Nullable;
// import lombok.Builder;
// import lombok.Getter;
// import lombok.RequiredArgsConstructor;

// @RestController
// @RequiredArgsConstructor
// @RequestMapping("/trip")
// public class TripController {

// private final TripService TripService;

// /**
// * Provide the details of a Trip with the given id.
// */
// @PostMapping("/{tripId}/reservation")
// public ResponseEntity<ReservationImageAnalysisResult>
// uploadReservationImage(@PathVariable
// Long tripId,
// @RequestParam("file") List<MultipartFile> files) {
// try {
// return ResponseEntity.ok().body(TripService.uploadReservationImage(tripId,
// files));
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of a Trip with the given id.
// */
// @GetMapping("/{tripId}")
// public ResponseEntity<TripDTO> trip(@PathVariable Long tripId) {
// try {
// return ResponseEntity.ok().body(TripService.getTrip(tripId));
// } catch (HttpClientErrorException e) {
// throw e;
// // return ResponseEntity.badRequest().body(e.getMessage());
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// @PostMapping("")
// // public ResponseEntity<TripDTO> createTrip(@RequestBody Trip newTrip) {
// public ResponseEntity<TripDTO> createTrip() {
// try {
// TripDTO tripDTO = TripService.createTrip();
// return ResponseEntity.created(Utils.getLocation(tripDTO.id())).body(tripDTO);
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Update the content of a Trip.
// */
// @PatchMapping("/{tripId}")
// public ResponseEntity<TripDTO> patchTrip(@PathVariable Long tripId,
// @RequestBody TripDTO newTripDTO) {
// try {
// return ResponseEntity.ok().body(TripService.patchTrip(tripId, newTripDTO));
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// @PostMapping("/{tripId}/todo")
// public ResponseEntity<TodoDTO> createTodo(@PathVariable Long tripId,
// @RequestBody CreateTodoRequest requestbody) {
// try {
// TodoDTO todoDTO = TripService.createTodo(tripId, requestbody.getPresetId(),
// requestbody.getCategory());
// return ResponseEntity.created(Utils.getLocation(todoDTO.id())).body(todoDTO);
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// @PatchMapping("/{tripId}/todo/{todoId}")
// public ResponseEntity<TodoDTO> patchTodo(@PathVariable Long todoId,
// @RequestBody TodoDTO newTodoDTO) {
// try {
// TodoDTO todoDTO = TripService.patchTodo(todoId, newTodoDTO);
// return ResponseEntity.ok().body(todoDTO);
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// @DeleteMapping("/{tripId}/todo/{todoId}")
// public ResponseEntity<Void> deleteTodo(@PathVariable Long todoId) {
// try {
// TripService.deleteTodo(todoId);
// return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// @GetMapping("/{tripId}/todoPreset")
// public ResponseEntity<List<PresetDTO>> todoPreset(@PathVariable
// Long tripId) {
// try {
// List<PresetDTO> presetTodoContentDTOs =
// TripService.getTodoPreset(tripId);
// return ResponseEntity.ok().body(presetTodoContentDTOs);
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// @PostMapping("/{tripId}/destination")
// public ResponseEntity<DestinationDTO> createDestination(@PathVariable Long
// tripId) {
// try {
// DestinationDTO destinationDTO = TripService.createDestination(tripId);
// return
// ResponseEntity.created(Utils.getLocation(destinationDTO.id())).body(destinationDTO);
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// @DeleteMapping("/{tripId}/destination/{destinationId}")
// public ResponseEntity<Void> deleteDestination(@PathVariable Long
// destinationId) {
// try {
// TripService.deleteDestination(destinationId);
// return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// // @GetMapping("/{tripId}/accomodation")
// // public ResponseEntity<List<AccomodationDTO>>
// accomodationPlan(@PathVariable
// // Long tripId) {
// // try {
// // return ResponseEntity.ok().body(TripService.getAccomodation(tripId));
// // } catch (HttpClientErrorException e) {
// // throw e;
// // }
// // }

// /**
// * Provide the details of an Trip with the given id.
// */
// @PostMapping("/{tripId}/accomodation")
// public ResponseEntity<AccomodationDTO> createAccomodation(@PathVariable Long
// tripId) {
// try {
// AccomodationDTO accomodationDTO = TripService.createAccomodation(tripId);
// return
// ResponseEntity.created(Utils.getLocation(accomodationDTO.id())).body(accomodationDTO);
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// @PatchMapping("/{tripId}/accomodation/{accomodationId}")
// public ResponseEntity<AccomodationDTO> patchAccomodation(@PathVariable Long
// accomodationId,
// @RequestBody AccomodationDTO newAccomodationDTO) {
// try {
// AccomodationDTO accomodationDTO =
// TripService.patchAccomodation(accomodationId, newAccomodationDTO);
// return ResponseEntity.ok().body(accomodationDTO);
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// @DeleteMapping("/{tripId}/accomodation/{accomodationId}")
// public ResponseEntity<Void> deleteAccomodation(@PathVariable Long
// accomodationId) {
// try {
// TripService.deleteAccomodation(accomodationId);
// return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
// } catch (HttpClientErrorException e) {
// throw e;
// }
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// // @PostMapping("/{tripId}/customTodo")
// // public ResponseEntity<TodoDTO> createCustomTodo(@PathVariable Long tripId,
// // @RequestBody String category) {
// // try {
// // TodoDTO todoDTO = TripService.createCustomTodo(tripId, category);
// // return
// ResponseEntity.created(Utils.getLocation(todoDTO.id())).body(todoDTO);
// // } catch (HttpClientErrorException e) {
// // throw e;
// // }
// // }

// /**
// * Provide the details of an Trip with the given id.
// */
// // @PostMapping("/{tripId}/presetTodo")
// // public ResponseEntity<List<TodoDTO>> createPresetTodo(@PathVariable Long
// // tripId,
// // @RequestBody List<Long> presetIds) {
// // try {
// // List<TodoDTO> todoDTOs = TripService.createPresetTodo(tripId, presetIds);
// // return
// // ResponseEntity.status(HttpStatus.SEE_OTHER).location(Utils.getLocation(new
// // Object())).body(todoDTOs);
// // } catch (HttpClientErrorException e) {
// // throw e;
// // }
// // }

// }
