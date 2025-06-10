package com.matchalab.trip_todo_api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PresetTodoContent extends TodoContent {

    @OneToMany(mappedBy = "todo", cascade = CascadeType.ALL, orphanRemoval = true)
    private Todo todo;

    public PresetTodoContent(Todo todo, Long id,
            String category,
            String type,
            String title,
            String iconId) {
        super(category, type, title, iconId);
        this.todo = todo;
    }
}