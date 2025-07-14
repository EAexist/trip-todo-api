package com.matchalab.trip_todo_api.service;

import java.awt.image.BufferedImage;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.imageio.ImageIO;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.matchalab.trip_todo_api.exception.TripNotFoundException;
import com.matchalab.trip_todo_api.model.Accomodation;
import com.matchalab.trip_todo_api.model.Flight;
import com.matchalab.trip_todo_api.model.Trip;
import com.matchalab.trip_todo_api.model.DTO.Reservation;
import com.matchalab.trip_todo_api.model.DTO.ReservationImageAnalysisResult;
import com.matchalab.trip_todo_api.repository.ReservationRepository;
import com.matchalab.trip_todo_api.repository.TripRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReservationService {

    @Autowired
    private final VisionService visionService;

    @Autowired
    private final GenAIService genAIService;

    @Autowired
    private final TripRepository tripRepository;

    @Autowired
    private final ReservationRepository reservationRepository;

    /**
     * Provide the details of a Trip with the given id.
     */
    public List<Reservation> getReservation(Long tripId) {
        List<Reservation> reservation = tripRepository.findById(tripId)
                .orElseThrow(() -> new TripNotFoundException(tripId)).getReservation();
        return reservation;
    }

    /**
     * Provide the details of a Trip with the given id.
     */
    public Reservation setLocalAppStorageFileUri(Long tripId, Long reservationId,
            String localAppStorageFileUri) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(() -> new TripNotFoundException(tripId));
        reservation.setLocalAppStorageFileUri(localAppStorageFileUri);

        return reservationRepository.save(reservation);
    }

    /**
     * Create new empty trip.
     */
    private ReservationImageAnalysisResult analyzeReservationTextAndCreateEntities(Long tripId, List<String> text) {

        /* Analyze Text with Generative AI */
        ReservationImageAnalysisResult reservationImageAnalysisResult = genAIService
                .extractInfofromReservationText(text);

        /* Save Data */
        ReservationImageAnalysisResult.ReservationImageAnalysisResultBuilder savedResultBuilder = ReservationImageAnalysisResult
                .builder();
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));

        List<Accomodation> accomodation = reservationImageAnalysisResult.accomodation();
        accomodation.stream().forEach(acc -> {
            acc.setTrip(trip);
        });
        trip.getAccomodation().addAll(accomodation);
        savedResultBuilder = savedResultBuilder
                .accomodation(tripRepository.save(trip).getAccomodation().subList(-1 * (accomodation.size()), -1));

        List<Flight> flight = reservationImageAnalysisResult.flight();
        flight.stream().forEach(fl -> {
            fl.setTrip(trip);
        });
        trip.getFlight().addAll(flight);
        savedResultBuilder = savedResultBuilder
                .flight(tripRepository.save(trip).getFlight().subList(-1 * (flight.size()), -1));

        return savedResultBuilder.build();
    }

    /**
     * Create new empty trip.
     */
    // private List<Reservation> analyzeFlightTicketTextAndCreateReservation(Long
    // tripId, List<String> text) {

    // /* Analyze Text with Generative AI */
    // // List<Reservation> reservation =
    // genAIService.extractReservationfromText(text,
    // // "항공권 모바일 티켓");
    // List<Reservation> reservation = Arrays.asList(new Reservation[] {
    // new Reservation(dateTimeISOString = "2025-06-29", "flightTicket", "항공권 모바일
    // 티켓", "") });

    // /* Save Data */
    // Reservation.ReservationBuilder savedResultBuilder = Reservation.builder();
    // Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new
    // TripNotFoundException(tripId));

    // reservation.stream().forEach(res -> {
    // res.setTrip(trip);
    // });
    // trip.getReservation().addAll(reservation);

    // return tripRepository.save(trip).getReservation().subList(-1 *
    // (reservation.size()), -1);
    // }

    private List<String> extractTextfromImage(List<MultipartFile> files) {
        List<String> reservationText = files.stream().map(multipartFile -> {
            try {
                BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
                return new InputStreamResource(multipartFile.getInputStream());
            } catch (Exception e) {
                return null;
            }
        })
                // .map(Utils::multipartFileToBufferedImage)
                // .map(Utils::bufferedImageToTiffResource)
                .map(visionService::extractTextfromImage)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        log.info(String.format("[extractTextfromImage] {}", reservationText.toString()));
        return reservationText;
    }

    /**
     * Create new empty trip.
     */
    public List<Reservation> analyzeFlightTicketAndCreateReservation(Long tripId,
            List<MultipartFile> files) {

        /* Extract Text from Image */
        List<String> reservationText = extractTextfromImage(files);

        /* Analyze Text with Generative AI */
        List<Reservation> reservation = Arrays
                .asList(new Reservation[] { new Reservation("2025-06-29", "flightTicket", "항공권 모바일 티켓", "인천 → 도쿠시마") });

        /* Save Data */
        Trip trip = tripRepository.findById(tripId).orElseThrow(() -> new TripNotFoundException(tripId));
        reservation.stream().forEach(res -> {
            res.setTrip(trip);
        });
        trip.getReservation().addAll(reservation);

        return tripRepository.save(trip).getReservation().subList(-1 * (reservation.size()), -1);
    }

    /**
     * Create new empty trip.
     */
    public ReservationImageAnalysisResult uploadReservationImage(Long tripId,
            List<MultipartFile> files) {

        /* Extract Text from Image */
        List<String> reservationText = files.stream().map(multipartFile -> {
            try {
                BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
                return new InputStreamResource(multipartFile.getInputStream());
            } catch (Exception e) {
                return null;
            }
        })
                // .map(Utils::multipartFileToBufferedImage)
                // .map(Utils::bufferedImageToTiffResource)
                .map(visionService::extractTextfromImage)
                .flatMap(List::stream)
                .collect(Collectors.toList());

        log.info(String.format("[extractTextfromImage] {}", reservationText.toString()));

        return analyzeReservationTextAndCreateEntities(tripId, reservationText);
    }

    /**
     * Create new empty trip.
     */
    public ReservationImageAnalysisResult uploadReservationText(Long tripId, String text) {

        return analyzeReservationTextAndCreateEntities(tripId, Arrays.asList(new String[] { text }));
    }

    /**
     * Create new empty trip.
     */
    public ReservationImageAnalysisResult uploadReservationLink(Long tripId, String url) {

        return analyzeReservationTextAndCreateEntities(tripId, Arrays.asList(new String[] { url }));
    }

}
