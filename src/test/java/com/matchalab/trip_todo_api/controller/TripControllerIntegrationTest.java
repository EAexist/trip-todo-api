package com.matchalab.trip_todo_api.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import com.matchalab.trip_todo_api.service.TripService;

// @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class TripControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    private TripController tripController;

    @Test
    void testTrip() throws Exception {

        mockMvc.perform(get("trip/0"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("name").value("John Doe"))
                .andExpect(jsonPath("number").value("1234567890"));
    }

    // @Test
    // void testCreateTrip() throws Exception {

    // }

    // @Test
    // void testUpdateTrip() throws Exception {

    // }

    // @Test
    // void testCreateTodo() throws Exception {

    // }

    // @Test
    // void testAccomodationDetail() throws Exception {

    // assertEquals("", "");

    // }

    // @Test
    // void testCreateAccomodation() throws Exception {

    // }
}
