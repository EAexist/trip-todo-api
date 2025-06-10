package com.matchalab.trip_todo_api.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.matchalab.trip_todo_api.model.mapper.TripMapper;
import com.matchalab.trip_todo_api.repository.AccomodationRepository;
import com.matchalab.trip_todo_api.repository.TodoRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;
import com.matchalab.trip_todo_api.service.TripService;

@Configuration
@ComponentScan(basePackageClasses = TripMapper.class)
public class TripConfig {

    @Bean
    TripService tripService(
            TripRepository tripRepository,
            TodoRepository todoRepository,
            AccomodationRepository accomodationRepository,
            TripMapper tripMapper) {
        return new TripService(tripRepository,
                todoRepository,
                accomodationRepository,
                tripMapper);
    }

    // @Bean
    // public TripRepository tripRepository() {
    // return new TripRepository();
    // }
}
