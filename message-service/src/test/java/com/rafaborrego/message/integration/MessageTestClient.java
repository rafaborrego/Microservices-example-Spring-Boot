package com.rafaborrego.message.integration;

import com.rafaborrego.message.controller.Endpoint;
import com.rafaborrego.message.dto.MessageOutputDto;
import com.rafaborrego.message.dto.MessagesDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Client to do requests on the message integration tests.
 * Some methods return a map instead of a message DTO so we can check the error message in the tests
 */

@Component
class MessageTestClient {

    @Autowired
    private TestRestTemplate restTemplate;

    ResponseEntity<Map> addMessage(String content) {

        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("content", content);

        return restTemplate.postForEntity(Endpoint.BASE_MESSAGE_URL, requestBodyMap, Map.class);
    }

    ResponseEntity<MessagesDto> getMessages() {

        return restTemplate.getForEntity(Endpoint.BASE_MESSAGE_URL, MessagesDto.class);
    }

    ResponseEntity<Map> modifyMessage(Long messageId, String content) {

        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("content", content);

        String modificationUrl = Endpoint.BASE_MESSAGE_URL + "/" + messageId;

        HttpEntity<MessageOutputDto> requestEntity = new HttpEntity(requestBodyMap);

        return restTemplate.exchange(modificationUrl, HttpMethod.PUT, requestEntity, Map.class);
    }

    ResponseEntity<Map> deleteMessage(Long messageId) {

        String deletionUrl = Endpoint.BASE_MESSAGE_URL + "/" + messageId;

        return restTemplate.exchange(deletionUrl, HttpMethod.DELETE, null, Map.class);
    }
}
