package com.rafaborrego.message.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.rafaborrego.message.dto.MessageOutputDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class IntegrationTestUtil {

    private static final String ERROR_MESSAGE_KEY = "message";


    static String getErrorMessage(ResponseEntity<Map> responseEntity) {

        return (String) responseEntity.getBody().get(ERROR_MESSAGE_KEY);
    }

    static MessageOutputDto convertMapToMessageDto(Map map) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return mapper.convertValue(map, MessageOutputDto.class);
    }
}
