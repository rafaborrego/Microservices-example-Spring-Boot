package com.rafaborrego.message.service;

import com.rafaborrego.message.exception.InvalidMessageContentException;
import com.rafaborrego.message.exception.MessageNotDeletableException;
import com.rafaborrego.message.exception.MessageNotFoundException;
import com.rafaborrego.message.exception.MessageNotModifiableException;
import com.rafaborrego.message.model.Message;
import com.rafaborrego.message.repository.MessageRepository;
import com.rafaborrego.message.client.UuidClient;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@Transactional
public class MessageServiceImpl implements MessageService {

    static final String ERROR_MESSAGE_CAN_ONLY_BE_MODIFIED_WITHIN_SECONDS_AFTER_BEING_CREATED = "Messages can only be modified within %d seconds after being created";
    static final String ERROR_MESSAGE_CAN_ONLY_BE_DELETED_AFTER_CERTAIN_MINUTES_AFTER_BEING_CREATED = "Messages can only be deleted after %d minutes after being created";
    static final String ERROR_MESSAGE_INVALID_CONTENT = "The message has invalid content";
    static final int MAX_SECONDS_MESSAGE_MODIFIABLE_AFTER_CREATION = 10;
    static final int MIN_MINUTES_BEFORE_MESSAGE_CAN_BE_DELETED_AFTER_CREATION = 2;
    private static final int SECONDS_PER_MINUTE = 60;

    private final MessageRepository messageRepository;

    private final UuidClient uuidClient;

    public MessageServiceImpl(MessageRepository messageRepository, UuidClient uuidClient) {
        this.messageRepository = messageRepository;
        this.uuidClient = uuidClient;
    }

    @Override
    public Message createMessage(String messageContent) {

        validateMessageContentIsSafe(messageContent);

        LocalDateTime currentDateTime = LocalDateTime.now();

        Message message = new Message();
        message.setContent(messageContent);
        message.setCreationTimestamp(currentDateTime);
        message.setLastUpdateTimestamp(currentDateTime);
        message.setUuid(getUuid());

        messageRepository.save(message);

        return message;
    }

    private void validateMessageContentIsSafe(String messageContent) {

        if (!XssValidator.isSafeString(messageContent)) {
            throw new InvalidMessageContentException(ERROR_MESSAGE_INVALID_CONTENT);
        }
    }

    /**
     * It returns null in case that the uuid cannot be retrieved so messages can be created when the external service is down.
     * Other approach would have been to throw an error in this case
     */
    private String getUuid() {

        return uuidClient.getUuid();
    }

    @Override
    public Message modifyMessage(Long messageId, String messageContent) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId));

        validateMessageContentIsSafe(messageContent);
        validateMessageCanBeModified(messageId, message);

        modifyMessage(messageContent, message);

        return message;
    }

    private void modifyMessage(String messageContent, Message message) {

        message.setContent(messageContent);
        message.setLastUpdateTimestamp(LocalDateTime.now());

        messageRepository.save(message);
    }

    private void validateMessageCanBeModified(Long messageId, Message message) {

        if (message.isDeleted()) {
            throw new MessageNotFoundException(messageId);
        }

        if (!wasMessageCreatedWithinLastSeconds(message)) {
            throw new MessageNotModifiableException(
                    String.format(ERROR_MESSAGE_CAN_ONLY_BE_MODIFIED_WITHIN_SECONDS_AFTER_BEING_CREATED,
                            MAX_SECONDS_MESSAGE_MODIFIABLE_AFTER_CREATION));
        }
    }

    private boolean wasMessageCreatedWithinLastSeconds(Message message) {

        long secondsDifference = getSecondsDifferenceBetweenDateTimes(message.getCreationTimestamp(), LocalDateTime.now());

        return secondsDifference <= MAX_SECONDS_MESSAGE_MODIFIABLE_AFTER_CREATION;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getMessages() {

        return messageRepository.findNonDeletedMessagesOrderedByCreationTimestamp();
    }

    /**
     * It could throw a not found error if the message is already deleted.
     * However I think that it is better to make the deletes idempotent
     */
    @Override
    public void deleteMessage(Long messageId) {

        Message message = messageRepository.findById(messageId)
                .orElseThrow(() -> new MessageNotFoundException(messageId));

        validateMessageCanBeDeleted(message);

        softDeleteMessage(message);
    }

    private void softDeleteMessage(Message message) {

        if (!message.isDeleted()) {

            message.setDeleted(true);
            message.setLastUpdateTimestamp(LocalDateTime.now());

            messageRepository.save(message);
        }
    }

    private void validateMessageCanBeDeleted(Message message) {

        if(!isMessageOlderThanCertainMinutes(message)) {

            throw new MessageNotDeletableException(
                    String.format(ERROR_MESSAGE_CAN_ONLY_BE_DELETED_AFTER_CERTAIN_MINUTES_AFTER_BEING_CREATED,
                            MIN_MINUTES_BEFORE_MESSAGE_CAN_BE_DELETED_AFTER_CREATION));
        }
    }

    private boolean isMessageOlderThanCertainMinutes(Message message) {

        long secondsDifference = getSecondsDifferenceBetweenDateTimes(message.getCreationTimestamp(), LocalDateTime.now());

        int minSecondsAfterCreation = MIN_MINUTES_BEFORE_MESSAGE_CAN_BE_DELETED_AFTER_CREATION * SECONDS_PER_MINUTE;

        return secondsDifference > minSecondsAfterCreation;
    }

    private long getSecondsDifferenceBetweenDateTimes(LocalDateTime fromDateTime, LocalDateTime toDateTime) {

        return fromDateTime.until(toDateTime, ChronoUnit.SECONDS);
    }
}
