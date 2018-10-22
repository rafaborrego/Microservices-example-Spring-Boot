package com.rafaborrego.message.contract;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.cloud.contract.stubrunner.StubFinder;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureStubRunner(ids = {"com.rafaborrego:uuid-service:+:stubs:8080"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class UuidContractTest {

    private static final String BASE_STUB_URL = "http://localhost:";
    private static final String GET_UUID_URL = "/uuid";
    private static final String EXPECTED_UUID = "BEGIN-56bb05bf-8f12-4a08-952f-9b2c0317fe11-END";

    private int producerPort;

    @Autowired
    private StubFinder stubFinder;

    @Before
    public void setup() {

        producerPort = stubFinder.findStubUrl("com.rafaborrego:uuid-service").getPort();
    }

    @Test
    public void getUuidShouldReturnUuid() {

        // When
        String endpointUrl = BASE_STUB_URL + producerPort + GET_UUID_URL;
        ResponseEntity<String> response = new TestRestTemplate().getForEntity(endpointUrl, String.class);

        // Then
        assertThat(response.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And
        assertThat(response.getBody(), is(equalTo(EXPECTED_UUID)));
    }
}
