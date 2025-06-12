package com.matchalab.trip_todo_api.model;

import java.util.Locale.Category;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
public class CustomTodoContent extends TodoContent {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    // @OneToOne
    // @JoinColumn(name = "todo_id", referencedColumnName = "id")
    // private Todo todo;

    public CustomTodoContent(Todo todo, Long id,
            String category,
            String type,
            String title,
            String iconId) {
        super(id, category, type, title, iconId);
        // this.todo = todo;
    }

    public CustomTodoContent(Todo todo, String category) {
        super(category);
        // this.todo = todo;
    }

    public CustomTodoContent(CustomTodoContent customTodoContent) {
        super(customTodoContent.getId(), customTodoContent.getCategory(), customTodoContent.getType(),
                customTodoContent.getTitle(), customTodoContent.getIconId());
    }
}