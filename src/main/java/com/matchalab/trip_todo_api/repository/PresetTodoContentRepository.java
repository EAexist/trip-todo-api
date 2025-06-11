package com.matchalab.trip_todo_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.matchalab.trip_todo_api.model.PresetTodoContent;

public interface PresetTodoContentRepository extends JpaRepository<PresetTodoContent, Long> {
}
