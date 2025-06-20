package com.matchalab.trip_todo_api.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class GenAIService {

    @Autowired
    private ResourceLoader resourceLoader;

    @Autowired
    private CloudVisionTemplate cloudVisionTemplate;

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

    public String extractReservationInfofromText(String text) {
        return text;
    }

}
