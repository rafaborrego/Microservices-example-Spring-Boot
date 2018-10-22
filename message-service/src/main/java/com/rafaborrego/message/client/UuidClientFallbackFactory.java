package com.rafaborrego.message.client;

import feign.hystrix.FallbackFactory;
import org.springframework.stereotype.Component;

@Component
public class UuidClientFallbackFactory implements FallbackFactory<UuidClient> {

    @Override
    public UuidClient create(Throwable errorCause) {

        return new UuidClientFallback(errorCause);
    }
}
