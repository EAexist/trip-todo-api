package com.matchalab.trip_todo_api.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class TodoContent {

    @Id
    private Long id;
    private String category;
    private String type;
    private String title;
    private String iconId;

    public TodoContent(String category) {
        this.category = category;
    }
}
