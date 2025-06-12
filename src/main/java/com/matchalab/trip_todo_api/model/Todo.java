package com.matchalab.trip_todo_api.model;

import jakarta.annotation.Nullable;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@RequiredArgsConstructor
@AllArgsConstructor
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String note;
    private String completeDateISOString;
    private int order_key;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trip_id")
    private Trip trip;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @Nullable
    private CustomTodoContent customTodoContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presetTodoContent_id")
    @Nullable
    private PresetTodoContent presetTodoContent;

    public Todo(Todo todo) {
        this.note = todo.getNote();
        this.completeDateISOString = todo.getCompleteDateISOString();
        this.order_key = todo.getOrder_key();
        this.customTodoContent = todo.getCustomTodoContent();
        this.presetTodoContent = todo.getPresetTodoContent();
    }
}
