package com.matchalab.trip_todo_api.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PresetTodoContent extends TodoContent {

    @Id
    private Long id;

    // @OneToMany(mappedBy = "presetTodoContent", cascade = CascadeType.ALL,
    // orphanRemoval = true)
    // private List<Todo> todo;

    public PresetTodoContent(List<Todo> todo, Long id,
            String category,
            String type,
            String title,
            String iconId) {
        super(id, category, type, title, iconId);
        this.id = id;
        // this.todo = todo;
    }
}