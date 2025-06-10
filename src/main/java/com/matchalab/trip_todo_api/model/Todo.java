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
import lombok.Setter;

@Entity
@Getter
@Setter
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "customTodoContent_id", referencedColumnName = "id")
    @Nullable
    private CustomTodoContent customTodoContent;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "presetTodoContent_id")
    @Nullable
    private PresetTodoContent presetTodoContent;

    public Todo() {
    }
}
