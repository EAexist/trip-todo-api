package com.matchalab.trip_todo_api.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.matchalab.trip_todo_api.service.TripService;

@WebMvcTest(TripController.class)
public class TripControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private TripService tripService;

    // @Test
    // void testAccomodationDetail() throws Exception {

    // }

    // @Test
    // void testCreateAccomodation() throws Exception {

    // }

    // @Test
    // void testCreateTodo() throws Exception {

    // }

    // @Test
    // void testCreateTrip() throws Exception {

    // }

    // @Test
    // void testTrip() throws Exception {

    // }

    // @Test
    // void testUpdateTrip() throws Exception {

    // }
}
