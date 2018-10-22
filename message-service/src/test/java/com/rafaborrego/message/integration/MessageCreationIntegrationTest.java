package com.rafaborrego.message.integration;

import com.rafaborrego.message.dto.MessageOutputDto;
import com.rafaborrego.message.dto.MessagesDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:testMessagesInsertionScript.sql"})
@AutoConfigureStubRunner(ids = {"com.rafaborrego:uuid-service:+:stubs:8080"},
        stubsMode = StubRunnerProperties.StubsMode.LOCAL)
public class MessageCreationIntegrationTest {

    private final static String MESSAGE_CONTENT = "Sample message";
    private final static String MESSAGE_WITH_XSS = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";
    private final static String EXPECTED_UUID = "BEGIN-56bb05bf-8f12-4a08-952f-9b2c0317fe11-END";

    @Autowired
    private MessageTestClient messageTestClient;

    @Test
    public void addMessageShouldCreateMessageWithExpectedFieldsAndStatusCreated() {

        // When
        ResponseEntity<Map> responseEntity = messageTestClient.addMessage(MESSAGE_CONTENT);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.CREATED)));

        // And
        MessageOutputDto createdMessage = IntegrationTestUtil.convertMapToMessageDto(responseEntity.getBody());
        assertThat(createdMessage.getContent(), is(equalTo(MESSAGE_CONTENT)));
        assertThat(createdMessage.getCreationTimestamp(), is(notNullValue()));
        assertThat(createdMessage.getLastUpdateTimestamp(), is(notNullValue()));
        assertThat(createdMessage.getLastUpdateTimestamp(), is(equalTo(createdMessage.getCreationTimestamp())));
    }

    @Test
    public void addMessageShouldReturnErrorWhenContentIsEmpty() {

        // When
        ResponseEntity<Map> responseEntity = messageTestClient.addMessage("  ");

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void addMessageShouldReturnErrorWhenMessageContainsXss() {

        // When
        ResponseEntity responseEntity = messageTestClient.addMessage(MESSAGE_WITH_XSS);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.UNPROCESSABLE_ENTITY)));

        // And
        String errorMessage = IntegrationTestUtil.getErrorMessage(responseEntity);
        String expectedErrorMessage = "The message has invalid content";

        assertThat(errorMessage, is(equalTo(expectedErrorMessage)));
    }

    @Test
    public void getMessagesShouldReturnMessageAfterCreatingIt() {

        // Given
        ResponseEntity<MessagesDto> responseEntity = messageTestClient.getMessages();
        List<MessageOutputDto> messagesBefore = responseEntity.getBody().getMessages();

        // When
        messageTestClient.addMessage(MESSAGE_CONTENT);

        // Then
        responseEntity = messageTestClient.getMessages();
        List<MessageOutputDto> messagesAfter = responseEntity.getBody().getMessages();

        assertThat(messagesAfter.size(), is(equalTo(messagesBefore.size() + 1)));
        assertThat(MESSAGE_CONTENT, is(equalTo(messagesAfter.get(0).getContent())));
    }

    @Test
    public void addMessageShouldCreateMessageWithExpectedUuidFromUuidService() {

        // When
        ResponseEntity<Map> responseEntity = messageTestClient.addMessage(MESSAGE_CONTENT);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.CREATED)));

        // And
        MessageOutputDto createdMessage = IntegrationTestUtil.convertMapToMessageDto(responseEntity.getBody());
        assertThat(createdMessage.getUuid(), is(equalTo(EXPECTED_UUID)));
    }
}
