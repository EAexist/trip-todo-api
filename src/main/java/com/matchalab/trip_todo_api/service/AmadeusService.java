// package com.matchalab.trip_todo_api.service;

// import org.springframework.beans.factory.annotation.Value;
// import org.springframework.boot.context.properties.ConfigurationProperties;
// import org.springframework.stereotype.Service;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.RestController;

// import com.amadeus.Amadeus;
// import com.amadeus.Params;
// import com.amadeus.exceptions.ResponseException;
// import com.amadeus.resources.Location;

// import lombok.extern.slf4j.Slf4j;

// @Service
// // @RequiredArgsConstructor
// @ConfigurationProperties(prefix = "amadeus")
// @Slf4j
// public class AmadeusService {

// @Value("${amadeus.client_id}")
// private String AMADEUS_CLIENT_ID;

// @Value("${amadeus.client_secret}")
// private String AMADEUS_CLIENT_SECRET;

// private Amadeus amadeus;
// // private final Amadeus amadeus = Amadeus.builder(AMADEUS_CLIENT_ID,
// // AMADEUS_CLIENT_SECRET).build();

// public AmadeusService() {
// log.info(String.format("[AMADEUS_CLIENT_ID] %s", this.AMADEUS_CLIENT_ID));
// log.info(String.format("[AMADEUS_CLIENT_SECRET] %s",
// this.AMADEUS_CLIENT_SECRET));
// amadeus = Amadeus.builder(AMADEUS_CLIENT_ID, AMADEUS_CLIENT_SECRET).build();
// }

// /**
// * Provide the details of an Trip with the given id.
// */
// public Location[] fetchLocations(String view, String subType, String keyword)
// throws ResponseException {
// log.info(String.format("[proxy/google-places-autocomplete] subType=%s ,
// keyword=%s", subType, keyword));

// Params params = Params.with("view", view).and("subType",
// subType).and("keyword", keyword);
// return amadeus.referenceData.locations.get(params);
// }

// }
