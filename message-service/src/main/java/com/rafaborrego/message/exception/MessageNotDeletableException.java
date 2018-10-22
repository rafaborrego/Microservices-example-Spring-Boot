package com.rafaborrego.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MessageNotDeletableException extends RuntimeException {

    public MessageNotDeletableException(String message) {
        super(message);
    }
}
