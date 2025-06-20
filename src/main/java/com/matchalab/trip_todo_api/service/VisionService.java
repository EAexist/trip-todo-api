package com.matchalab.trip_todo_api.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class VisionService {

    // @Autowired
    // private final ResourceLoader resourceLoader;

    @Autowired
    private final CloudVisionTemplate cloudVisionTemplate;

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

    // public Accomodation extractAccomodationfromPdf(String pdfUrl) {
    // String text = extractTextfromImage(pdfUrl);
    // return new Accomodation();
    // }

    // public Accomodation extractAccomodationfromImage(String imageUrl) {
    // String text = extractTextfromImage(imageUrl);
    // return new Accomodation();
    // }

    public String extractTextfromImage(Resource resource) {
        String text = cloudVisionTemplate.extractTextFromImage(resource);
        return text;
    }

    // public List<String> extractTextFromPdf(String pdfUrl) {
    // List<String> texts =
    // this.cloudVisionTemplate.extractTextFromPdf(this.resourceLoader.getResource(pdfUrl));
    // return texts;
    // }

}
