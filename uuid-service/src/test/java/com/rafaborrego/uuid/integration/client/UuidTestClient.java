package com.rafaborrego.uuid.integration.client;

import com.rafaborrego.uuid.controller.Endpoint;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@Component
public class UuidTestClient {

    private final TestRestTemplate restTemplate;

    public UuidTestClient(TestRestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    public ResponseEntity<String> generateUuid() {

        return restTemplate.getForEntity(Endpoint.BASE_UUID_URL, String.class);
    }
}
