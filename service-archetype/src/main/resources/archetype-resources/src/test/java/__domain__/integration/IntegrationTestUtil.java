package ${groupId}.${domain}.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import org.springframework.http.ResponseEntity;

import java.util.Map;

class IntegrationTestUtil {

    private static final String ERROR_MESSAGE_KEY = "message";


    static String getErrorMessage(ResponseEntity<Map> responseEntity) {

        return (String) responseEntity.getBody().get(ERROR_MESSAGE_KEY);
    }

    static ${domainCamelCase}OutputDto convertMapTo${domainCamelCase}Dto(Map map) {

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());

        return mapper.convertValue(map, ${domainCamelCase}OutputDto.class);
    }
}
