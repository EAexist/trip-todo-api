package com.matchalab.trip_todo_api.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class CustomTodoContent extends TodoContent {

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "todo_id", referencedColumnName = "id")
    private Todo todo;

    public CustomTodoContent(Todo todo, Long id,
            String category,
            String type,
            String title,
            String iconId) {
        super(category, type, title, iconId);
        this.todo = todo;

    }
}