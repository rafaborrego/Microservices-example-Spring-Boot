package ${groupId}.${domain}.integration;

import ${groupId}.${domain}.controller.Endpoint;
import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}sDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Client to do requests on the ${domain} integration tests.
 * Some methods return a map instead of a ${domain} DTO so we can check the error ${domain} in the tests
 */

@Component
class ${domainCamelCase}TestClient {

    @Autowired
    private TestRestTemplate restTemplate;

    ResponseEntity<${domainCamelCase}OutputDto> get${domainCamelCase}ById(Long ${domain}Id) {

        return restTemplate.getForEntity(Endpoint.BASE_DOMAIN_URL + "/" + ${domain}Id, ${domainCamelCase}OutputDto.class);
    }

    ResponseEntity<${domainCamelCase}sDto> get${domainCamelCase}s() {

        return restTemplate.getForEntity(Endpoint.BASE_DOMAIN_URL, ${domainCamelCase}sDto.class);
    }

    ResponseEntity<Map> create${domainCamelCase}(String content) {

        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("content", content);

        return restTemplate.postForEntity(Endpoint.BASE_DOMAIN_URL, requestBodyMap, Map.class);
    }

    ResponseEntity<Map> modify${domainCamelCase}(Long ${domain}Id, String content) {

        Map<String, String> requestBodyMap = new HashMap<>();
        requestBodyMap.put("content", content);

        String modificationUrl = Endpoint.BASE_DOMAIN_URL + "/" + ${domain}Id;

        HttpEntity<${domainCamelCase}OutputDto> requestEntity = new HttpEntity(requestBodyMap);

        return restTemplate.exchange(modificationUrl, HttpMethod.PUT, requestEntity, Map.class);
    }

    ResponseEntity<Map> delete${domainCamelCase}(Long ${domain}Id) {

        String deletionUrl = Endpoint.BASE_DOMAIN_URL + "/" + ${domain}Id;

        return restTemplate.exchange(deletionUrl, HttpMethod.DELETE, null, Map.class);
    }
}
