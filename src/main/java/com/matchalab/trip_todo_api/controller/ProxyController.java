package com.matchalab.trip_todo_api.controller;

import java.net.URI;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/proxy")
@Slf4j
public class ProxyController {

    private final String GOOGLE_PLACES_API_KEY = "AIzaSyClYZkWHBqRmV-UUFclFZPx2MeVl8RgTlI";
    private final String GOOGLE_PLACES_API_BASE_URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json";

    /**
     * Provide the details of an Trip with the given id.
     */

    @GetMapping("/place/autocomplete/json")
    public Object googlePlaceAutocomplete(@RequestParam String input,
            @RequestParam String language, @RequestParam String type) {
        log.info(String.format("[proxy/google-places-autocomplete] input=%s , language=%s", input, language));
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(GOOGLE_PLACES_API_BASE_URL)
                .queryParam("input", input)
                .queryParam("key", GOOGLE_PLACES_API_KEY)
                .queryParam("language", language)
                .queryParam("type", type)
                // .queryParams(query)
                .build()
                .toUri();
        log.info(String.format("[proxy/google-places-autocomplete] uri={}", uri.toString()));
        return restTemplate.getForObject(uri, Object.class);
    }

    /**
     * Provide the details of an Trip with the given id.
     */

    @GetMapping("/airport/place/autocomplete/json")
    public Object googlePlaceAirportAutocomplete(@RequestParam String input,
            @RequestParam String language) {
        log.info(String.format("[proxy/google-places-autocomplete] input=%s , language=%s", input, language));
        RestTemplate restTemplate = new RestTemplate();
        URI uri = UriComponentsBuilder.fromUriString(GOOGLE_PLACES_API_BASE_URL)
                .queryParam("input", input)
                .queryParam("key", GOOGLE_PLACES_API_KEY)
                .queryParam("language", language)
                .queryParam("type", "airport")
                // .queryParams(query)
                .build()
                .toUri();
        log.info(String.format("[proxy/google-places-autocomplete] uri={}", uri.toString()));
        return restTemplate.getForObject(uri, Object.class);
    }

}
