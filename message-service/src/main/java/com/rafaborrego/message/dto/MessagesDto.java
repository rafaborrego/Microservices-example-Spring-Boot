package com.rafaborrego.message.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * We could return just the list of messages instead but this allows to add in the future pagination data (number of pages, etc.)
 * without breaking the existing consumers
 */
public class MessagesDto {

    @ApiModelProperty(notes = "The list of messages")
    private List<MessageOutputDto> messages;

    public List<MessageOutputDto> getMessages() {
        return messages;
    }

    public void setMessages(List<MessageOutputDto> messages) {
        this.messages = messages;
    }
}
