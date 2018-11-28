package com.rafaborrego.uuid.integration;

import com.rafaborrego.uuid.integration.client.HealthTestClient;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;

import java.util.Map;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;

public class HealthIntegrationTest {

    private static final String STATUS_MAP_KEY = "status";

    @Autowired
    private HealthTestClient healthTestClient;

    private ResponseEntity<Map> responseEntity;

    @When("the client calls the health endpoint")
    public void the_client_calls_the_health_endpoint() {

        responseEntity = healthTestClient.getServiceStatus();
    }

    @Then("the health response status code should be {int}")
    public void the_health_response_status_code_should_be(Integer expectedHttpStatus) {

        assertThat(responseEntity.getStatusCode().value(), is(equalTo(expectedHttpStatus)));
    }

    @Then("the result should be {string}")
    public void the_result_should_be(String expectedStatus) {

        String status = (String) responseEntity.getBody().get(STATUS_MAP_KEY);
        assertThat(status, is(equalTo(expectedStatus)));
    }
}
