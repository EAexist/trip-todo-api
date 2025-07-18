package com.matchalab.trip_todo_api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Stream;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.ai.model.tool.ToolCallingManager;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatOptions;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.retry.support.RetryTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.spring.vision.CloudVisionTemplate;
import com.google.cloud.vertexai.VertexAI;
import com.google.cloud.vision.v1.ImageAnnotatorClient;
import com.matchalab.trip_todo_api.model.Flight;
import com.matchalab.trip_todo_api.model.FlightTicket;
import com.matchalab.trip_todo_api.model.DTO.AccomodationDTO;
import com.matchalab.trip_todo_api.model.DTO.ReservationImageAnalysisResult;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("local")
// @TestPropertySource(properties = {
// "spring.config.location=classpath:application-dev.yml" })
public class ReservationServiceTest {

    // private VisionService visionService;

    private ReservationService reservationService;

    @BeforeEach
    public void setup() throws IOException {
        ImageAnnotatorClient imageAnnotatorClient = ImageAnnotatorClient.create();
        VisionService visionService = new VisionService(new CloudVisionTemplate(imageAnnotatorClient));
        GenAIService genAIService = new GenAIService(
                new VertexAiGeminiChatModel(new VertexAI(),
                        VertexAiGeminiChatOptions.builder().model("gemini-2.0-flash-lite").build(),
                        ToolCallingManager.builder().build(),
                        new RetryTemplate(), null));
        reservationService = new ReservationService(visionService, genAIService,
                null,
                null);
    }

    @Test
    void Given_AccomodationReservation_Agoda_IOSAppScreenshot_testUploadReservationImage() throws IOException {
        AccomodationDTO expectedAccomodationDTO = new AccomodationDTO(null, "호텔 PAQ 도쿠시마 (HOSTEL PAQ tokushima)",
                "도미토리 내 1인 예약 (혼성)", 1, "HYEONPYO", "2025-02-24", "2025-02-25", "15:00", null,
                "10:00", "도쿠시마", null, new HashMap<String, String>());
        String[] filePaths = { "/image/accomodation-agoda-app-ios_1.tiff", "/image/accomodation-agoda-app-ios_2.tiff" };
        List<MultipartFile> files = readFiles(filePaths);
        ReservationImageAnalysisResult ReservationImageAnalysisResult = reservationService.uploadReservationImage(0L,
                files);

        assertThat(ReservationImageAnalysisResult).isNotNull();
        assertThat(ReservationImageAnalysisResult.flight()).isEmpty();
        assertThat(ReservationImageAnalysisResult.flightTicket()).isEmpty();
        assertThat(ReservationImageAnalysisResult.accomodation()).isNotEmpty();
        assertThat(ReservationImageAnalysisResult.accomodation().getFirst()).usingRecursiveComparison()
                .ignoringFieldsOfTypes()
                .ignoringFields()
                .isEqualTo(expectedAccomodationDTO);
    }

    @Test
    void Given_FlightReservation_Easterjet_KakaotalkScreenshot_testUploadReservationImage() throws IOException {
        Flight expectedFlight = new Flight();
        String[] filePaths = { "/image/flightReservation_Easterjet_KakaotalkScreenshot_1.png" };
        List<MultipartFile> files = readFiles(filePaths);
        ReservationImageAnalysisResult ReservationImageAnalysisResult = reservationService.uploadReservationImage(0L,
                files);

        assertThat(ReservationImageAnalysisResult).isNotNull();
        assertThat(ReservationImageAnalysisResult.accomodation()).isEmpty();
        assertThat(ReservationImageAnalysisResult.flightTicket()).isEmpty();
        assertThat(ReservationImageAnalysisResult.flight()).isNotEmpty();
        assertThat(ReservationImageAnalysisResult.flight().getFirst()).usingRecursiveComparison()
                .ignoringFieldsOfTypes()
                .ignoringFields()
                .isEqualTo(expectedFlight);
    }

    @Test
    void Given_FlightTicket_Easterjet_MobileWebScreenshot_testUploadReservationImage() throws IOException {
        FlightTicket expectedFlightTicket = FlightTicket.builder().build();
        String[] filePaths = { "/image/flightTicket_Easterjet_mobileWebScreenshot_1.png" };
        List<MultipartFile> files = readFiles(filePaths);
        ReservationImageAnalysisResult ReservationImageAnalysisResult = reservationService.uploadReservationImage(0L,
                files);

        assertThat(ReservationImageAnalysisResult).isNotNull();
        assertThat(ReservationImageAnalysisResult.flight()).isEmpty();
        assertThat(ReservationImageAnalysisResult.accomodation()).isEmpty();
        assertThat(ReservationImageAnalysisResult.flightTicket()).isNotEmpty();
        assertThat(ReservationImageAnalysisResult.flightTicket().getFirst()).usingRecursiveComparison()
                .ignoringFieldsOfTypes()
                .ignoringFields()
                .isEqualTo(expectedFlightTicket);
    }

    @Test
    void Given_FlightTicket_Easterjet_Image_testUploadReservationImage() throws IOException {
        FlightTicket expectedFlightTicket = FlightTicket.builder().build();
        String[] filePaths = { "/image/flightTicket_Easterjet_image_1.png" };
        List<MultipartFile> files = readFiles(filePaths);
        ReservationImageAnalysisResult ReservationImageAnalysisResult = reservationService.uploadReservationImage(0L,
                files);

        assertThat(ReservationImageAnalysisResult).isNotNull();
        assertThat(ReservationImageAnalysisResult.flight()).isEmpty();
        assertThat(ReservationImageAnalysisResult.accomodation()).isEmpty();
        assertThat(ReservationImageAnalysisResult.flightTicket()).isNotEmpty();
        assertThat(ReservationImageAnalysisResult.flightTicket().getFirst()).usingRecursiveComparison()
                .ignoringFieldsOfTypes()
                .ignoringFields()
                .isEqualTo(expectedFlightTicket);
    }

    List<MultipartFile> readFiles(String[] filePaths) {
        return Stream.of(filePaths).map(filePath -> {
            try {
                File _file = new ClassPathResource(filePath).getFile();
                return (MultipartFile) new MockMultipartFile(_file.getName(), new FileInputStream(_file));
            } catch (Exception e) {
                return null;
            }
        }).toList();
    }
}
