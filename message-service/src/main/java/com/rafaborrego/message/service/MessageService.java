package com.rafaborrego.message.service;

import com.rafaborrego.message.model.Message;

import java.util.List;

public interface MessageService {

    Message createMessage(String content);

    Message modifyMessage(Long messageId, String messageContent);

    List<Message> getMessages();

    void deleteMessage(Long messageId);
}
