package com.rafaborrego.uuid.integration.client;

import com.rafaborrego.uuid.controller.Endpoint;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * Client to do requests on the health integration tests.
 */

@Component
public class HealthTestClient {

    @Autowired
    private TestRestTemplate restTemplate;

    public ResponseEntity<Map> getServiceStatus() {

        return restTemplate.getForEntity(Endpoint.HEALTH_ENDPOINT, Map.class);
    }
}
