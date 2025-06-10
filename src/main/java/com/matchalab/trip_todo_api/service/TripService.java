package com.matchalab.trip_todo_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matchalab.trip_todo_api.exception.TripNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.repository.AccomodationRepository;
import com.matchalab.trip_todo_api.repository.TodoRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class TripService {

    private final TripRepository tripRepository;
    private final TodoRepository todoRepository;
    private final AccomodationRepository accomodationRepository;
    private final TripMapper tripMapper;

    /**
     * Provide the details of a Trip with the given id.
     */
    public TripDTO getTrip(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        return tripMapper.mapToTripDTO(trip);
    }

    /**
     * Update the content of a Trip.
     */
    public TripDTO putTrip(Trip newTrip) {
        Trip trip = tripRepository.save(newTrip);
        return tripMapper.mapToTripDTO(trip);
    }

    /**
     * Create new empty trip.
     */
    public TripDTO createTrip() {
        Trip trip = tripRepository.save(new Trip());
        return tripMapper.mapToTripDTO(trip);
    }

    /**
     * Create new empty todo.
     */
    public TodoDTO createTodo(Long tripId) {
        Todo newTodo = new Todo();
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        newTodo.setOrder_key(0);
        newTodo.setTrip(trip);
        return tripMapper.mapToTodoDTO(todoRepository.save(newTodo));
        // return tripRepository.findById(tripId).orElseThrow(() -> new
        // TripNotFoundException(tripId));
    }

    /**
     * Provide the details of a Accomodation with the given trip_id.
     */
    public List<Accomodation> getAccomodation(Long tripId) {
        List<Accomodation> accomodations = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId)).getAccomodation();
        return accomodations;
    }

    /**
     * Create new empty accomodation.
     */
    public Accomodation createAccomodation(Accomodation newAccomodation) {
        Accomodation accomodation = accomodationRepository.save(newAccomodation);
        return accomodation;
    }
}
