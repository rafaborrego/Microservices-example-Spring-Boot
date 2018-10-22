package com.rafaborrego.message.service;

import com.rafaborrego.message.exception.MessageNotDeletableException;
import com.rafaborrego.message.exception.MessageNotModifiableException;
import com.rafaborrego.message.model.Message;
import com.rafaborrego.message.repository.MessageRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;
import org.mockito.Mockito;

import java.time.LocalDateTime;
import java.util.Optional;

import static junit.framework.TestCase.fail;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.core.Is.is;
import static org.mockito.Mockito.when;

@RunWith(JUnit4.class)
public class MessageServiceTest {

    private MessageService messageService;

    private MessageRepository messageRepository;

    private static final Long MESSAGE_ID = 1L;
    private static final String INITIAL_CONTENT = "Initial content";
    private static final String NEW_CONTENT = "New content";

    @Before
    public void setUp() {

        messageRepository = Mockito.mock(MessageRepository.class);
        messageService = new MessageServiceImpl(messageRepository, null);
    }

    @Test
    public void modifyShouldModifyMessageWhenModificationIsLessThanTenSecondsAfterMessageIsCreated() {

        // Given
        Message message = createTestMessage(MESSAGE_ID, INITIAL_CONTENT, 9);
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(Optional.of(message));

        // When
        Message modifiedMessage = messageService.modifyMessage(MESSAGE_ID, NEW_CONTENT);

        // Then
        assertThat(NEW_CONTENT, is(equalTo(modifiedMessage.getContent())));
    }

    @Test
    public void modifyShouldModifyMessageWhenModificationIsTenSecondsAfterMessageIsCreated() {

        // Given
        Message message = createTestMessage(MESSAGE_ID, INITIAL_CONTENT, 10);
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(Optional.of(message));

        // When
        Message modifiedMessage = messageService.modifyMessage(MESSAGE_ID, NEW_CONTENT);

        // Then
        assertThat(NEW_CONTENT, is(equalTo(modifiedMessage.getContent())));
    }

    @Test
    public void modifyShouldThrowErrorWhenModificationIsMoreThanTenSecondsAfterMessageIsCreated() {

        // Given
        Message message = createTestMessage(MESSAGE_ID, INITIAL_CONTENT, 11);
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(Optional.of(message));

        // When
        String errorMessage = null;
        try {
            messageService.modifyMessage(MESSAGE_ID, NEW_CONTENT);
            fail("It should had failed because it tried to modify the message more than 10 seconds after being created");
        } catch (MessageNotModifiableException exception) {
            errorMessage = exception.getMessage();
        }

        // Then
        String expectedErrorMessage = String.format(MessageServiceImpl.ERROR_MESSAGE_CAN_ONLY_BE_MODIFIED_WITHIN_SECONDS_AFTER_BEING_CREATED,
                MessageServiceImpl.MAX_SECONDS_MESSAGE_MODIFIABLE_AFTER_CREATION);

        assertThat(expectedErrorMessage, is(equalTo(errorMessage)));
    }

    @Test
    public void deleteShouldDeleteMessageWhenDeletionIsMoreThanTwoMinutesAfterMessageIsCreated() {

        // Given
        Message message = createTestMessage(MESSAGE_ID, INITIAL_CONTENT, 121);
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(Optional.of(message));

        // When
        messageService.deleteMessage(MESSAGE_ID);

        // Then
        // No error should be thrown
    }

    @Test
    public void deleteShouldThrowErrorWhenDeletionIsTwoMinutesAfterMessageIsCreated() {

        // Given
        Message message = createTestMessage(MESSAGE_ID, INITIAL_CONTENT, 120);
        when(messageRepository.findById(MESSAGE_ID)).thenReturn(Optional.of(message));

        // When
        String errorMessage = null;
        try {
            messageService.deleteMessage(MESSAGE_ID);
            fail("It should had failed because it tried to delete the message two minutes after being created");
        } catch (MessageNotDeletableException exception) {
            errorMessage = exception.getMessage();
        }

        // Then
        String expectedErrorMessage = String.format(MessageServiceImpl.ERROR_MESSAGE_CAN_ONLY_BE_DELETED_AFTER_CERTAIN_MINUTES_AFTER_BEING_CREATED,
                MessageServiceImpl.MIN_MINUTES_BEFORE_MESSAGE_CAN_BE_DELETED_AFTER_CREATION);

        assertThat(expectedErrorMessage, is(equalTo(errorMessage)));
    }

    private Message createTestMessage(Long messageId, String content, int secondsDifference) {

        Message message = new Message();
        message.setId(messageId);
        message.setContent(content);
        message.setCreationTimestamp(LocalDateTime.now().minusSeconds(secondsDifference));

        return message;
    }
}
