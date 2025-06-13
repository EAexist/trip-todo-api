package com.matchalab.trip_todo_api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.io.ClassPathResource;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.matchalab.trip_todo_api.model.PresetTodoContent;
import com.matchalab.trip_todo_api.model.Todo;
import com.matchalab.trip_todo_api.model.DTO.PresetTodoContentDTO;
import com.matchalab.trip_todo_api.repository.PresetTodoContentRepository;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class DataLoader implements CommandLineRunner {

    @Autowired
    private PresetTodoContentRepository presetTodoContentRepository;
    // @Autowired
    // private TripRepository tripRepository;
    // @Autowired
    // private AccomodationRepository accomodationRepository;
    // @Autowired
    // private DestinationRepository destinationRepository;

    @Override
    @Transactional
    public void run(String... args) throws Exception {

        try {
            presetTodoContentRepository.saveAll(readPresetJson());

        } catch (DataIntegrityViolationException ignore) {
            ignore.printStackTrace();
        }
    }

    public static List<PresetTodoContent> readPresetJson() throws Exception {
        List<PresetTodoContent> presets = new ArrayList<PresetTodoContent>();
        try {
            ObjectMapper mapper = new ObjectMapper();
            presets = mapper.readValue((new ClassPathResource("static/todoPreset.json")).getInputStream(),
                    new TypeReference<List<PresetTodoContent>>() {
                    });

        } catch (IOException e) {
            e.printStackTrace();
        }
        return presets;
    }
}