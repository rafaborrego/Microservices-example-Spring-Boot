package com.rafaborrego.message.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

@FeignClient(name = "uuid-service", fallbackFactory = UuidClientFallbackFactory.class)
public interface UuidClient {

    @GetMapping(value = "/uuid")
    String getUuid();
}
