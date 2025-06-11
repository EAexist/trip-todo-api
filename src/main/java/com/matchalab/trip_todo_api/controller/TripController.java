package com.matchalab.trip_todo_api.controller;

import java.net.URI;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import lombok.RequiredArgsConstructor;

import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.service.TripService;

@RestController
@RequiredArgsConstructor
@RequestMapping("/trip")
public class TripController {

    private final TripService TripService;

    /**
     * Provide the details of a Trip with the given id.
     */
    @GetMapping("/{tripId}")
    public ResponseEntity<TripDTO> trip(@PathVariable Long tripId) {
        try {
            return ResponseEntity.ok().body(TripService.getTrip(tripId));
        } catch (HttpClientErrorException e) {
            throw e;
            // return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    /**
     * Update the content of a Trip.
     */
    @PutMapping("/{tripId}")
    public ResponseEntity<TripDTO> updateTrip(@PathVariable Long tripId, @RequestBody TripDTO newTripDTO) {
        try {
            return ResponseEntity.ok().body(TripService.putTrip(tripId, newTripDTO));
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    /**
     * Provide the details of an Trip with the given id.
     */
    @PostMapping("")
    // public ResponseEntity<TripDTO> createTrip(@RequestBody Trip newTrip) {
    public ResponseEntity<TripDTO> createTrip() {
        try {
            TripDTO tripDTO = TripService.createTrip();
            return ResponseEntity.created(getLocation(tripDTO.id())).body(tripDTO);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    /**
     * Provide the details of an Trip with the given id.
     */
    @PostMapping("/{tripId}/todo")
    public ResponseEntity<TodoDTO> createTodo(@PathVariable Long tripId, @RequestBody String category) {
        try {
            TodoDTO todoDTO = TripService.createTodo(tripId, category);
            return ResponseEntity.created(getLocation(todoDTO.id())).body(todoDTO);
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    /**
     * Provide the details of an Trip with the given id.
     */
    @GetMapping("/{tripId}/accomodation")
    public ResponseEntity<List<Accomodation>> accomodationDetail(@PathVariable Long tripId) {
        try {
            return ResponseEntity.ok().body(TripService.getAccomodation(tripId));
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    /**
     * Provide the details of an Trip with the given id.
     */
    @PostMapping("/{tripId}/accomodation")
    public ResponseEntity<Accomodation> createAccomodation(@PathVariable Long tripId,
            @RequestBody Accomodation newAccomodation) {
        try {
            return ResponseEntity.ok().body(TripService.createAccomodation(newAccomodation));
        } catch (HttpClientErrorException e) {
            throw e;
        }
    }

    private URI getLocation(Object resourceId) {
        return ServletUriComponentsBuilder.fromCurrentRequestUri().path("/{childId}").buildAndExpand(resourceId)
                .toUri();
    }
}
