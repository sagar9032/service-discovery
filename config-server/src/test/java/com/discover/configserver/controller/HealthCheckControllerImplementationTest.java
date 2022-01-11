package com.discover.configserver.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class HealthCheckControllerImplementationTest {

    @Autowired private TestRestTemplate restTemplate;
    String url = "http://localhost:8888/health";

    @Test
    void healthCheck() {
        ResponseEntity<String> result = restTemplate.withBasicAuth("user", "password").getForEntity(url, String.class);
        System.out.println(result.getBody());
        assertThat(result.getBody()).contains("Hello from 'EXTERNAL-CONFIG-SERVER'!");
        assertEquals(HttpStatus.OK, result.getStatusCode());
    }

}