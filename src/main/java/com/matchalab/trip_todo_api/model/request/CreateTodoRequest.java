package com.matchalab.trip_todo_api.model.request;

import jakarta.annotation.Nullable;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class CreateTodoRequest {

    @Nullable
    private String category;

    @Nullable
    private Long presetId;
}