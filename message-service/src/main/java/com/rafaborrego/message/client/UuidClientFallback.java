package com.rafaborrego.message.client;

import feign.FeignException;
import org.springframework.http.HttpStatus;

/**
 * Handles the scenarios in which the external APIs are not available (down, slow to respond, etc.)
 */
public class UuidClientFallback implements UuidClient {

    private final Throwable errorCause;

    public UuidClientFallback(Throwable errorCause) {
        this.errorCause = errorCause;
    }

    /**
     * Degraded service: I have chosen to return null so it is possible to create messages when the other service is down.
     * The UUID could be populated later, e.g. via a script or a scheduler that runs every certain time and calls the service
     * to retrieve uuids and fill the missing ones. Other approach would be to throw an exception so messages
     * are not created without UUID
     */
    @Override
    public String getUuid() {

        if (isErrorCauseNotFound()) {

            throw new IllegalStateException("It was not possible to get the UUID from that url", errorCause);
        }

        return null;
    }

    private boolean isErrorCauseNotFound() {

        return errorCause instanceof FeignException
                && ((FeignException) errorCause).status() == HttpStatus.NOT_FOUND.value();
    }
}
