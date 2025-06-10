package com.matchalab.trip_todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchalab.trip_todo_api.model.Destination;

public interface DestinationRepository extends JpaRepository<Destination, Long> {

    // List<Todo> findByLastName(String lastName);
    // Todo findById(long id);
}
