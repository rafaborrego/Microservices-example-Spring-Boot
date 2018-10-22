package com.rafaborrego.message.controller;

import com.rafaborrego.message.dto.MessageInputDto;
import com.rafaborrego.message.dto.MessageOutputDto;
import com.rafaborrego.message.dto.MessagesDto;
import com.rafaborrego.message.exception.InvalidNumberMessagesException;
import com.rafaborrego.message.mapper.BeanMapper;
import com.rafaborrego.message.model.Message;
import com.rafaborrego.message.service.MessageService;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = Endpoint.BASE_MESSAGE_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class MessageControllerImpl implements MessageController {

    private static final int MINIMUM_NUMBER_MESSAGES_GET = 1;
    private static final String ERROR_MESSAGE_INVALID_NUMBER_MESSAGES = "The minimum number of messages to retrieve is " + MINIMUM_NUMBER_MESSAGES_GET;


    private final BeanMapper beanMapper;

    private final MessageService messageService;

    public MessageControllerImpl(BeanMapper beanMapper, MessageService messageService) {
        this.beanMapper = beanMapper;
        this.messageService = messageService;
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public MessageOutputDto addMessage(@ApiParam("Message data") @Validated @RequestBody MessageInputDto messageData) {

        Message message = messageService.createMessage(messageData.getContent());

        return beanMapper.map(message, MessageOutputDto.class);
    }

    @Override
    @PutMapping(Endpoint.PATH_MESSAGE_ID)
    public MessageOutputDto modifyMessage(@ApiParam("Message id") @PathVariable Long messageId,
                                          @ApiParam("Message data") @Validated @RequestBody MessageInputDto messageData) {

        Message message = messageService.modifyMessage(messageId, messageData.getContent());

        return beanMapper.map(message, MessageOutputDto.class);
    }

    @Override
    @GetMapping
    public MessagesDto getMessages() {

        List<Message> messages = messageService.getMessages();

        return convertMessagesToMessagesDto(messages);
    }

    @Override
    @DeleteMapping(Endpoint.PATH_MESSAGE_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteMessage(@ApiParam("Message id") @PathVariable Long messageId) {

        messageService.deleteMessage(messageId);
    }

    private MessagesDto convertMessagesToMessagesDto(List<Message> messages) {

        List<MessageOutputDto> convertedMessages = beanMapper.mapAsList(messages, MessageOutputDto.class);

        MessagesDto messagesDto = new MessagesDto();
        messagesDto.setMessages(convertedMessages);

        return messagesDto;
    }
}
