package com.matchalab.trip_todo_api.model;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter
@Setter
public class PresetTodoContent extends TodoContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToMany(mappedBy = "presetTodoContent", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Todo> todo;

    public PresetTodoContent(List<Todo> todo, Long id,
            String category,
            String type,
            String title,
            String iconId) {
        super(category, type, title, iconId);
        this.todo = todo;
    }
}