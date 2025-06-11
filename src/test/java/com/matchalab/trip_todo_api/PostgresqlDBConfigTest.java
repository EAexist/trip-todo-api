package com.matchalab.trip_todo_api;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.ConfigDataApplicationContextInitializer;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@TestInstance(Lifecycle.PER_CLASS)
@SpringJUnitConfig(classes = {}, initializers = ConfigDataApplicationContextInitializer.class)
@TestPropertySource(properties = { "spring.config.location = classpath:application-test.yml" })
@ActiveProfiles("test")
public class PostgresqlDBConfigTest {

    @Value("${spring.datasource.url}")
    private String url;

    @Value("${spring.datasource.username}")
    private String username;

    @Value("${spring.datasource.password}")
    private String password;

    @BeforeAll
    void setUp() {
        log.info("[PostgresqlDbConfigTest] Trying to connect DB:");
        log.info("url={}", url);
        log.info("username={}", username);
        log.info("password={}", password);
    }

    @Test
    void DBConnectionTest() throws SQLException {
        Connection conn = DriverManager.getConnection(url, username, password);
        log.info("연결 정보 확인: {}", conn);
        assertNotNull(conn);
    }
}