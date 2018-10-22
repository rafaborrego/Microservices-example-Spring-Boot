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

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:testMessagesInsertionScript.sql"})
public class MessageSearchIntegrationTest {

    @Autowired
    private MessageTestClient messageTestClient;

    @Test
    public void getMessagesShouldReturnAllNonDeletedMessagesOrderedByCreationDate() {

        // Given
        int expectedNumberMessages = 5;

        // When
        ResponseEntity<MessagesDto> responseEntity = messageTestClient.getMessages();
        List<MessageOutputDto> messages = responseEntity.getBody().getMessages();

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And
        assertThat(messages.size(), is(equalTo(expectedNumberMessages)));
        assertTrue(areMessagesOrderedByCreationDate(messages));
        assertFalse(messagesContainDeletedMessages(messages));
    }

    private boolean areMessagesOrderedByCreationDate(List<MessageOutputDto> messages) {

        boolean ordered = true;

        int index = 1;
        while (ordered && index < messages.size()) {

            if (messages.get(index - 1).getCreationTimestamp()
                    .compareTo(messages.get(index).getCreationTimestamp()) < 0) {
                ordered = false;
            } else {
                index ++;
            }
        }

        return ordered;
    }

    private boolean messagesContainDeletedMessages(List<MessageOutputDto> messages) {

        return messages.stream().anyMatch(message -> message.isDeleted());
    }
}
