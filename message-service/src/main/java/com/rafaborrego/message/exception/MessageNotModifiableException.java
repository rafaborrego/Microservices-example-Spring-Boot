package com.rafaborrego.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class MessageNotModifiableException extends RuntimeException {

    public MessageNotModifiableException(String message) {
        super(message);
    }
}
