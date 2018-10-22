package com.rafaborrego.message.integration;

import com.rafaborrego.message.dto.MessageOutputDto;
import com.rafaborrego.message.dto.MessagesDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:testMessagesInsertionScript.sql"})
public class MessageDeletionIntegrationTest {

    private static final Long ACTIVE_MESSAGE_ID = 25L;
    private static final Long DELETED_MESSAGE_ID = 26L;
    private static final Long NON_EXISTING_MESSAGE_ID = 100L;

    @Autowired
    private MessageTestClient messageTestClient;

    @Test
    public void deleteMessageShouldReturnEmptyBodyAndNoContentStatusWhenDeletionAfterTwoMinutes() {

        // When
        ResponseEntity responseEntity = messageTestClient.deleteMessage(ACTIVE_MESSAGE_ID);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));

        // And
        assertThat(responseEntity.getBody(), is(nullValue()));
    }

    @Test
    public void deleteMessageShouldReturnErrorWhenDeletionAfterLessThanTwoMinutes() {

        // Given
        ResponseEntity<Map> creationResponseEntity = messageTestClient.addMessage("Content");
        MessageOutputDto message = IntegrationTestUtil.convertMapToMessageDto(creationResponseEntity.getBody());

        // When
        ResponseEntity deletionResponseEntity = messageTestClient.deleteMessage(message.getId());

        // Then
        assertThat(deletionResponseEntity.getStatusCode(), is(equalTo(HttpStatus.FORBIDDEN)));

        // And
        String errorMessage = IntegrationTestUtil.getErrorMessage(deletionResponseEntity);
        String expectedErrorMessage = "Messages can only be deleted after 2 minutes after being created";

        assertThat(errorMessage, is(equalTo(expectedErrorMessage)));
    }

    @Test
    public void deleteShouldNotFailWhenDeletingDeletedMessage() {

        // When
        ResponseEntity responseEntity = messageTestClient.deleteMessage(DELETED_MESSAGE_ID);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));

        // And
        assertThat(responseEntity.getBody(), is(nullValue()));
    }

    @Test
    public void deleteMessageShouldReturnErrorWhenMessageDoesNotExist() {

        // When
        ResponseEntity responseEntity = messageTestClient.deleteMessage(NON_EXISTING_MESSAGE_ID);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));

        // And
        String errorMessage = IntegrationTestUtil.getErrorMessage(responseEntity);
        String expectedErrorMessage = "The message " + NON_EXISTING_MESSAGE_ID + " was not found";

        assertThat(errorMessage, is(equalTo(expectedErrorMessage)));
    }

    @Test
    public void getMessagesShouldNotReturnMessageAfterDeletingIt() {

        // Given
        ResponseEntity<MessagesDto> responseEntity = messageTestClient.getMessages();
        MessagesDto messagesBeforeDeleting = responseEntity.getBody();

        // When
        messageTestClient.deleteMessage(ACTIVE_MESSAGE_ID);

        // Then
        responseEntity = messageTestClient.getMessages();
        MessagesDto messagesAfterDeleting = responseEntity.getBody();

        assertTrue(containsMessageWithId(messagesBeforeDeleting, ACTIVE_MESSAGE_ID));
        assertFalse(containsMessageWithId(messagesAfterDeleting, ACTIVE_MESSAGE_ID));
    }

    private boolean containsMessageWithId(MessagesDto messagesDto, Long messageId) {

        return messagesDto.getMessages().stream()
                .anyMatch(message -> message.getId().equals(messageId));
    }
}
