package com.rafaborrego.message.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HealthIntegrationTest {

    private static final String UP_STATUS = "UP";
    private static final String STATUS_MAP_KEY = "status";

    @Autowired
    private HealthTestClient healthTestClient;

    @Test
    public void shouldGetStatusUp() {

        // When
        ResponseEntity<Map> responseEntity = healthTestClient.getServiceStatus();

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And
        String status = (String) responseEntity.getBody().get(STATUS_MAP_KEY);
        assertThat(status, is(equalTo(UP_STATUS)));
    }
}
