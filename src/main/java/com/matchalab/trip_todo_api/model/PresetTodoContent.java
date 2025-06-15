package com.matchalab.trip_todo_api.model;

import java.util.List;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PresetTodoContent extends TodoContent {

    @Id
    private Long id;

    // @OneToMany(mappedBy = "presetTodoContent", cascade = CascadeType.ALL,
    // orphanRemoval = true)
    // private List<Todo> todo;

    public PresetTodoContent(Long id,
            String category,
            String type,
            String title,
            String iconId) {
        super(category, type, title, iconId);
        this.id = id;
    }

    public PresetTodoContent(PresetTodoContent presetTodoContent) {
        super(presetTodoContent.getCategory(), presetTodoContent.getType(),
                presetTodoContent.getTitle(), presetTodoContent.getIconId());
        this.id = presetTodoContent.getId();
    }
}