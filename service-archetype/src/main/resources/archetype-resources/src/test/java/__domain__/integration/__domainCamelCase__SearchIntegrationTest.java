package ${groupId}.${domain}.integration;

import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}sDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:test${domainCamelCase}sInsertionScript.sql"})
public class ${domainCamelCase}SearchIntegrationTest {

    @Autowired
    private ${domainCamelCase}TestClient ${domain}TestClient;

    @Test
    public void get${domainCamelCase}sShouldReturnAllNonDeleted${domainCamelCase}s() {

        // Given
        int expectedNumber${domainCamelCase}s = 5;

        // When
        ResponseEntity<${domainCamelCase}sDto> responseEntity = ${domain}TestClient.get${domainCamelCase}s();
        List<${domainCamelCase}OutputDto> ${domain}s = responseEntity.getBody().get${domainCamelCase}s();

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And
        assertThat(${domain}s.size(), is(equalTo(expectedNumber${domainCamelCase}s)));
        assertFalse(${domain}sContainDeleted${domainCamelCase}s(${domain}s));
    }
    
    private boolean ${domain}sContainDeleted${domainCamelCase}s(List<${domainCamelCase}OutputDto> ${domain}s) {

        return ${domain}s.stream().anyMatch(${domain} -> ${domain}.isDeleted());
    }
}
