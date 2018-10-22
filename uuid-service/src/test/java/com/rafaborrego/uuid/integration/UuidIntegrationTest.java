package com.rafaborrego.uuid.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UuidIntegrationTest {

    private final static String EXPECTED_PREFIX = "Test_prefix";
    private final static String EXPECTED_SUFFIX = "text_suffix";
    private final static String UUID_REGULAR_EXPRESSION = "[0-9a-fA-F\\-]{36}";

    @Autowired
    private UuidTestClient uuidTestClient;

    @Test
    public void generateUuidShouldReturnUuidWithPrefixAndSuffix() {

        // When
        ResponseEntity<String> responseEntity = uuidTestClient.generateUuid();

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And

        String generatedUuid = responseEntity.getBody();
        String regExpUuidWithPrefixAndSuffix = EXPECTED_PREFIX + "-" + UUID_REGULAR_EXPRESSION + "-" +  EXPECTED_SUFFIX;

        assertTrue(generatedUuid.matches(regExpUuidWithPrefixAndSuffix));
    }

    @Test
    public void generateUuidShouldReturnDifferentUuidsWhenCalledTwice() {

        // When
        ResponseEntity<String> responseEntity = uuidTestClient.generateUuid();
        String firstUuid = responseEntity.getBody();

        responseEntity = uuidTestClient.generateUuid();
        String secondUuid = responseEntity.getBody();

        // Then
        assertThat(secondUuid, is(not(equalTo(firstUuid))));
    }
}
