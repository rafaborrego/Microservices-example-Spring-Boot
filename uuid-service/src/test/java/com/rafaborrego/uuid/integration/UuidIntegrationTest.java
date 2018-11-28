package com.rafaborrego.uuid.integration;

import com.rafaborrego.uuid.integration.client.UuidTestClient;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UuidIntegrationTest {

    @Autowired
    private UuidTestClient uuidTestClient;

    private ResponseEntity<String> responseEntity;

    private String previousUuid;


    @When("the client calls the UUID generation endpoint")
    public void the_client_calls_the_UUID_generation_endpoint() {

        responseEntity = uuidTestClient.generateUuid();
    }

    @Then("the uuid response status code should be {int}")
    public void the_uuid_response_status_code_should_be(Integer expectedHttpStatus) {

        assertThat(responseEntity.getStatusCode().value(), is(equalTo(expectedHttpStatus)));
    }

    @Then("the UUID should start by {string}")
    public void the_UUID_should_start_by(String expectedPrefix) {

        String generatedUuid = responseEntity.getBody();
        assertTrue(generatedUuid.startsWith(expectedPrefix));
    }

    @Then("the UUID should end by {string}")
    public void the_UUID_should_end_by(String expectedSuffix) {

        String generatedUuid = responseEntity.getBody();
        assertTrue(generatedUuid.endsWith(expectedSuffix));
    }

    @Given("the client has retrieved a UUID before")
    public void the_client_has_retrieved_a_UUID_before() {

        previousUuid = uuidTestClient.generateUuid().getBody();
    }

    @Then("the UUID should be different to the one generated before")
    public void the_UUID_should_be_different_to_the_one_generated_before() {

        String generatedUuid = responseEntity.getBody();
        assertThat(generatedUuid, is(not(equalTo(previousUuid))));
    }
}
