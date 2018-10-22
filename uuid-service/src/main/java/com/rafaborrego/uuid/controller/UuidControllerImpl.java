package com.rafaborrego.uuid.controller;

import com.rafaborrego.uuid.service.UuidService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = Endpoint.BASE_UUID_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class UuidControllerImpl implements UuidController {

    @Value("${api.uuidPrefix}")
    private String uuidPrefix;

    @Value("${api.uuidSuffix}")
    private String uuidSuffix;

    private final UuidService uuidService;

    public UuidControllerImpl(UuidService uuidService) {
        this.uuidService = uuidService;
    }

    @Override
    @GetMapping
    public String generateUuid() {

        return uuidService.generateUuid(uuidPrefix, uuidSuffix);
    }
}
