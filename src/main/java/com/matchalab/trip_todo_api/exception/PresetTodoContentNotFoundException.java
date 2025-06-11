package com.matchalab.trip_todo_api.exception;

public class PresetTodoContentNotFoundException extends RuntimeException {

    public PresetTodoContentNotFoundException(Long id) {
        super("Could not find PresetTodoContent " + id);
    }
}
