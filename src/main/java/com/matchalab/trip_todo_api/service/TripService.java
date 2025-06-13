package com.matchalab.trip_todo_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchalab.trip_todo_api.exception.NotFoundException;
import com.matchalab.trip_todo_api.exception.PresetTodoContentNotFoundException;
import com.matchalab.trip_todo_api.exception.TripNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.CustomTodoContent;
import com.matchalab.trip_todo_api.model.Destination;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.DestinationDTO;
import com.matchalab.trip_todo_api.model.DTO.PresetTodoContentDTO;
import com.matchalab.trip_todo_api.model.DTO.TodoDTO;
import com.matchalab.trip_todo_api.model.DTO.TripDTO;
import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.repository.AccomodationRepository;
import com.matchalab.trip_todo_api.repository.DestinationRepository;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;
import com.matchalab.trip_todo_api.repository.TodoRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
    private final DestinationRepository destinationRepository;
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
     * Create new empty trip.
     */
    public TripDTO createTrip() {
        Trip trip = tripRepository.save(new Trip());
        return tripMapper.mapToTripDTO(trip);
    }

    /**
     * Update the content of a Trip.
     */
    public TripDTO patchTrip(Long tripId, TripDTO newTripDTO) {
        Trip trip = tripMapper.updateTripFromDto(newTripDTO,
                tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId)));
        return tripMapper.mapToTripDTO(tripRepository.save(trip));
    }

    /**
     * Create new todo.
     */
    public TodoDTO createTodo(Long tripId, Long presetId, String category) {
        Todo newTodo = new Todo();
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        newTodo.setOrderKey(0);
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
     * Change contents or orderKey of todo.
     */
    public TodoDTO patchTodo(Long todoId, TodoDTO newTodoDTO) {
        Todo todo = tripMapper.mapToTodo(tripMapper.updateTodoDTOFromDto(newTodoDTO, tripMapper.mapToTodoDTO(
                todoRepository.findById(todoId).orElseThrow(() -> new NotFoundException(todoId)))));
        return tripMapper.mapToTodoDTO(todoRepository.save(todo));
    }

    /**
     * Create new todo.
     */
    public void deleteTodo(Long todoId) {
        accomodationRepository.findById(todoId).orElseThrow(() -> new NotFoundException(todoId));
        todoRepository.deleteById(todoId);
    }

    /**
     * Create new todo.
     */
    public List<PresetTodoContentDTO> getTodoPreset(Long tripId) {
        return presetTodoContentRepository.findAll().stream().map(tripMapper::mapToPresetTodoContentDTO).toList();
    }

    /**
     * Create new empty trip.
     */
    public DestinationDTO createDestination(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        Destination newDestination = new Destination();
        newDestination.setTrip(trip);
        trip.getDestination().add(newDestination);
        return tripMapper.mapToDestinationDTO(tripRepository.save(trip).getDestination().getLast());
    }

    /**
     * Create new todo.
     */
    public void deleteDestination(Long destinationId) {
        accomodationRepository.findById(destinationId).orElseThrow(() -> new NotFoundException(destinationId));
        destinationRepository.deleteById(destinationId);
    }

    /**
     * Provide the details of a Accomodation with the given trip_id.
     */
    public List<AccomodationDTO> getAccomodation(Long tripId) {
        List<AccomodationDTO> accomodations = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId)).getAccomodation().stream()
                .map(accomodation -> tripMapper.mapToAccomodationDTO(accomodation)).toList();
        return accomodations;
    }

    /**
     * Create new empty accomodation.
     */
    public AccomodationDTO createAccomodation(Long tripId) {
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        Accomodation newAccomodation = new Accomodation();
        newAccomodation.setTrip(trip);
        trip.getAccomodation().add(newAccomodation);
        return tripMapper.mapToAccomodationDTO(tripRepository.save(trip).getAccomodation().getLast());
    }

    /**
     * Change contents of accomodation.
     */
    public AccomodationDTO patchAccomodation(Long accomodationId, AccomodationDTO newAccomodationDTO) {
        Accomodation accomodation = tripMapper.updateAccomodationFromDto(newAccomodationDTO,
                accomodationRepository.findById(accomodationId)
                        .orElseThrow(() -> new NotFoundException(accomodationId)));

        return tripMapper.mapToAccomodationDTO(accomodationRepository.save(accomodation));
    }

    /**
     * Create new todo.
     */
    public void deleteAccomodation(Long accomodationId) {
        accomodationRepository.findById(accomodationId).orElseThrow(() -> new NotFoundException(accomodationId));
        accomodationRepository.deleteById(accomodationId);
    }
    /**
     * Create new empty custom todo.
     */
    // public TodoDTO createCustomTodo(Long tripId, String category) {
    // Todo newTodo = new Todo();
    // Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new
    // TripNotFoundException(tripId));
    // newTodo.setOrderKey(0);
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
    // newTodo.setOrderKey(0);
    // newTodo.setTrip(trip);
    // newTodo.setPresetTodoContent(presetTodoContentRepository.findById(presetId)
    // .orElseThrow(() -> new PresetTodoContentNotFoundException(presetId)));
    // return tripMapper.mapToTodoDTO(todoRepository.save(newTodo));
    // }).toList();
    // }
}
