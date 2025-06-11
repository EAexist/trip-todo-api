package com.matchalab.trip_todo_api.model;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TodoContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String category;
    private String type;
    private String title;
    private String iconId;

    public TodoContent(
            String category,
            String type,
            String title,
            String iconId) {
        this.category = category;
        this.type = type;
        this.title = title;
        this.iconId = iconId;
    }

    public TodoContent(
            String category) {
        this.category = category;
    }
}
