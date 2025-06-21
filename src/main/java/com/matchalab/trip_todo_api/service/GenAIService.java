package com.matchalab.trip_todo_api.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.ReservationDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenAIService {

    /**
     * This method downloads an image from a URL and sends its contents to the
     * Vision API for label
     * detection.
     *
     * @param imageUrl the URL of the image
     * @param map      the model map to use
     * @return a string with the list of labels and percentage of certainty
     * @throws com.google.cloud.spring.vision.CloudVisionException if the Vision API
     *                                                             call produces an
     *                                                             error
     */

    public ReservationDTO extractReservationInfofromText(List<String> text, String options) {
        return new ReservationDTO(new AccomodationDTO(null, options, options, 0, options, options, options, options,
                options, options, options, options, null));
    }

}
