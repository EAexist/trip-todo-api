package com.matchalab.trip_todo_api.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.cloud.gcp.vision.CloudVisionTemplate;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.web.multipart.MultipartFile;

import com.google.cloud.vision.v1.ImageAnnotatorClient;

/* https://github.com/spring-attic/spring-cloud-gcp/blob/main/spring-cloud-gcp-vision/src/test/java/org/springframework/cloud/gcp/vision/CloudVisionTemplateTests.java */
@ExtendWith(MockitoExtension.class)
// @EnabledIfSystemProperty(named = "it.vision", matches = "true")
public class VisionServiceTest {

    @Mock
    private ImageAnnotatorClient imageAnnotatorClient;

    private VisionService visionService;

    @BeforeEach
    public void setupVisionTemplateMock() {
        imageAnnotatorClient = Mockito.mock(ImageAnnotatorClient.class);
        visionService = new VisionService(new CloudVisionTemplate(imageAnnotatorClient));
    }

    @Test
    void testExtractTextFromPdf() {
    }

    @Test
    void testExtractTextfromImage() {
        MultipartFile file;
        try {
            File _file = new ClassPathResource("/image/accomodation-agoda-app-ios_1.png").getFile();
            file = new MockMultipartFile(_file.getName(), new FileInputStream(_file));
            String text = visionService.extractTextfromImage(file.getResource());
            assertThat(text).isEqualTo("text");
        } catch (IOException e) {
            // throw (e);
        }
    }
}
