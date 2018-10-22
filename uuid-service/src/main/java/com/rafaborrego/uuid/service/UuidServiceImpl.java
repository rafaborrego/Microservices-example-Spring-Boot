package com.rafaborrego.uuid.service;

import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UuidServiceImpl implements UuidService {

    @Override
    public String generateUuid(final String uuidPrefix, final String uuidSuffix) {

        final String uuid = generateUuid();

        return String.format("%s-%s-%s", uuidPrefix, uuid, uuidSuffix);
    }

    private String generateUuid() {

        final UUID uuidGenerator = UUID.randomUUID();

        return uuidGenerator.toString();
    }
}
