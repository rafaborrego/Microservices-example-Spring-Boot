package com.rafaborrego.message.integration;

import com.rafaborrego.message.dto.MessageOutputDto;
import com.rafaborrego.message.dto.MessagesDto;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:testMessagesInsertionScript.sql"})
public class MessageModificationIntegrationTest {

    private static final Long ACTIVE_MESSAGE_ID = 25L;
    private static final Long DELETED_MESSAGE_ID = 26L;
    private static final Long NON_EXISTING_MESSAGE_ID = 100L;
    private static final String MESSAGE_WITH_XSS = "<p><a href='http://example.com/' onclick='stealCookies()'>Link</a></p>";

    @Autowired
    private MessageTestClient messageTestClient;

    @Test
    public void modifyMessageShouldReturnModifiedMessageWhenExecutedLessThanTenSecondsAfterCreation() {

        // Given
        String contentBefore = "Before";
        String contentAfter = "After";

        ResponseEntity<Map> creationResponseEntity = messageTestClient.addMessage(contentBefore);
        MessageOutputDto message = IntegrationTestUtil.convertMapToMessageDto(creationResponseEntity.getBody());
        String uuidBefore = message.getUuid();
        LocalDateTime creationTimeBefore = message.getCreationTimestamp();
        LocalDateTime lastUpdatedTimeBefore = message.getLastUpdateTimestamp();

        // When
        ResponseEntity<Map> modificationResponseEntity = messageTestClient.modifyMessage(message.getId(), contentAfter);

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And
        MessageOutputDto messageDto = IntegrationTestUtil.convertMapToMessageDto(modificationResponseEntity.getBody());
        String messageContentAfter = messageDto.getContent();
        String uuidAfter = messageDto.getUuid();
        LocalDateTime creationTimeAfter = messageDto.getCreationTimestamp();
        LocalDateTime lastUpdatedTimeAfter = messageDto.getLastUpdateTimestamp();

        assertThat(messageContentAfter, is(equalTo(contentAfter)));
        assertThat(uuidAfter, is(equalTo(uuidBefore)));
        assertThat(creationTimeAfter, is(equalTo(creationTimeBefore)));
        assertThat(lastUpdatedTimeAfter, greaterThan(lastUpdatedTimeBefore));
    }

    @Test
    public void modifyMessageShouldReturnErrorWhenExecutedMoreThanTenSecondsAfterCreation() {

        // When
        ResponseEntity<Map> modificationResponseEntity = messageTestClient.modifyMessage(ACTIVE_MESSAGE_ID, "content");

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.FORBIDDEN)));

        // And
        String errorMessage = IntegrationTestUtil.getErrorMessage(modificationResponseEntity);
        String expectedErrorMessage = "Messages can only be modified within 10 seconds after being created";

        assertThat(errorMessage, is(equalTo(expectedErrorMessage)));
    }

    @Test
    public void modifyMessageShouldReturnErrorWhenContentIsEmpty() {

        // When
        ResponseEntity<Map> modificationResponseEntity = messageTestClient.modifyMessage(ACTIVE_MESSAGE_ID, "  ");

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.BAD_REQUEST)));
    }

    @Test
    public void modifyMessageShouldReturnErrorWhenMessageContainsXss() {

        // When
        ResponseEntity responseEntity = messageTestClient.modifyMessage(ACTIVE_MESSAGE_ID, MESSAGE_WITH_XSS);

        // Then
        assertThat(responseEntity.getStatusCode(), Matchers.is(Matchers.equalTo(HttpStatus.UNPROCESSABLE_ENTITY)));

        // And
        String errorMessage = IntegrationTestUtil.getErrorMessage(responseEntity);
        String expectedErrorMessage = "The message has invalid content";

        assertThat(errorMessage, Matchers.is(Matchers.equalTo(expectedErrorMessage)));
    }

    @Test
    public void modifyMessageShouldReturnErrorWhenMessageDoesNotExist() {

        // When
        ResponseEntity<Map> modificationResponseEntity = messageTestClient.modifyMessage(NON_EXISTING_MESSAGE_ID, "content");

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));

        // And
        String errorMessage = IntegrationTestUtil.getErrorMessage(modificationResponseEntity);
        String expectedErrorMessage = "The message " + NON_EXISTING_MESSAGE_ID + " was not found";

        assertThat(errorMessage, is(equalTo(expectedErrorMessage)));
    }

    @Test
    public void modifyMessageShouldReturnErrorWhenMessageIsDeleted() {

        // When
        ResponseEntity<Map> modificationResponseEntity = messageTestClient.modifyMessage(DELETED_MESSAGE_ID, "content");

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));

        // And
        String errorMessage = IntegrationTestUtil.getErrorMessage(modificationResponseEntity);
        String expectedErrorMessage = "The message " + DELETED_MESSAGE_ID + " was not found";

        assertThat(errorMessage, is(equalTo(expectedErrorMessage)));
    }

    @Test
    public void getMessagesShouldReturnModifiedMessage() {

        // Given
        String messageBefore = "Content before";
        ResponseEntity<Map> createdMessagesResponseEntity = messageTestClient.addMessage(messageBefore);
        MessageOutputDto message = IntegrationTestUtil.convertMapToMessageDto(createdMessagesResponseEntity.getBody());

        // When
        String newContent = "New content";
        messageTestClient.modifyMessage(message.getId(), newContent);

        // Then
        ResponseEntity<MessagesDto> messagesAfterResponseEntity = messageTestClient.getMessages();
        String messageContentAfter = findMessageContentById(messagesAfterResponseEntity.getBody(), message.getId());

        assertThat(messageContentAfter, is(equalTo(newContent)));
    }

    private String findMessageContentById(MessagesDto messagesDto, Long messageId) {

        return messagesDto.getMessages().stream()
                .filter(message -> messageId.equals(message.getId()))
                .findFirst().get()
                .getContent();
    }
}
