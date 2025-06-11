package com.matchalab.trip_todo_api.model;

import io.micrometer.common.lang.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTodoRequest {

    @Nullable
    private String category;

    @Nullable
    private Long presetId;
    // getters and setters
}