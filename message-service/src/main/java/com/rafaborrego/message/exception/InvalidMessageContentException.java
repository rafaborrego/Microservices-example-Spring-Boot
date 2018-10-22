package com.rafaborrego.message.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InvalidMessageContentException extends RuntimeException {

    public InvalidMessageContentException(String message) {
        super(message);
    }
}
