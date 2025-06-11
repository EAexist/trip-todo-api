package com.matchalab.trip_todo_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.matchalab.trip_todo_api.exception.PresetTodoContentNotFoundException;
import com.matchalab.trip_todo_api.exception.TripNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.repository.AccomodationRepository;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;
import com.matchalab.trip_todo_api.repository.TodoRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class TripService {

    @Autowired
    private final TripRepository tripRepository;
    @Autowired
    private final TodoRepository todoRepository;
    @Autowired
    private final PresetTodoContentRepository presetTodoContentRepository;
    @Autowired
    private final AccomodationRepository accomodationRepository;
    @Autowired
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
    public TripDTO putTrip(Long tripId, TripDTO newTripDTO) {
        Trip trip = tripMapper.mapToTrip(newTripDTO);
        trip.setId(tripId);
        return tripMapper.mapToTripDTO(tripRepository.save(trip));
    }

    /**
     * Create new empty trip.
     */
    public TripDTO createTrip() {
        Trip trip = tripRepository.save(new Trip());
        return tripMapper.mapToTripDTO(trip);
    }

    /**
     * Create new todo.
     */
    public TodoDTO createTodo(Long tripId, Long presetId, String category) {
        Todo newTodo = new Todo();
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        newTodo.setOrder_key(0);
        newTodo.setTrip(trip);

        if (presetId != null) {
            newTodo.setPresetTodoContent(presetTodoContentRepository.findById(presetId)
                    .orElseThrow(() -> new PresetTodoContentNotFoundException(presetId)));
        } else {
            newTodo.setCustomTodoContent(new CustomTodoContent(newTodo, category));
        }

        return tripMapper.mapToTodoDTO(todoRepository.save(newTodo));
    }

    /**
     * Create new todo.
     */
    public List<PresetTodoContent> getTodoPreset(Long tripId) {
        return presetTodoContentRepository.findAll();
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
    /**
     * Create new empty custom todo.
     */
    // public TodoDTO createCustomTodo(Long tripId, String category) {
    // Todo newTodo = new Todo();
    // Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new
    // TripNotFoundException(tripId));
    // newTodo.setOrder_key(0);
    // newTodo.setTrip(trip);
    // newTodo.setCustomTodoContent(new CustomTodoContent(newTodo, category));
    // return tripMapper.mapToTodoDTO(todoRepository.save(newTodo));
    // }

    /**
     * Create new preset todo.
     */
    // public List<TodoDTO> createPresetTodo(Long tripId, List<Long> presetIds) {
    // return presetIds.stream().map((presetId) -> {
    // Todo newTodo = new Todo();
    // Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new
    // TripNotFoundException(tripId));
    // newTodo.setOrder_key(0);
    // newTodo.setTrip(trip);
    // newTodo.setPresetTodoContent(presetTodoContentRepository.findById(presetId)
    // .orElseThrow(() -> new PresetTodoContentNotFoundException(presetId)));
    // return tripMapper.mapToTodoDTO(todoRepository.save(newTodo));
    // }).toList();
    // }
}
