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

import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:test${domainCamelCase}sInsertionScript.sql"})
public class ${domainCamelCase}DeletionIntegrationTest {

    private static final Long ACTIVE_DOMAIN_ID = 25L;
    private static final Long DELETED_DOMAIN_ID = 26L;
    private static final Long NON_EXISTING_DOMAIN_ID = 100L;

    @Autowired
    private ${domainCamelCase}TestClient ${domain}TestClient;

    @Test
    public void delete${domainCamelCase}ShouldReturnEmptyBodyAndNoContentStatusWhenDeletesExisting${domainCamelCase}() {

        // When
        ResponseEntity responseEntity = ${domain}TestClient.delete${domainCamelCase}(ACTIVE_DOMAIN_ID);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));

        // And
        assertThat(responseEntity.getBody(), is(nullValue()));
    }
    
    @Test
    public void deleteShouldNotFailWhenDeletingDeleted${domainCamelCase}() {

        // When
        ResponseEntity responseEntity = ${domain}TestClient.delete${domainCamelCase}(DELETED_DOMAIN_ID);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NO_CONTENT)));

        // And
        assertThat(responseEntity.getBody(), is(nullValue()));
    }

    @Test
    public void delete${domainCamelCase}ShouldReturnErrorWhen${domainCamelCase}DoesNotExist() {

        // When
        ResponseEntity responseEntity = ${domain}TestClient.delete${domainCamelCase}(NON_EXISTING_DOMAIN_ID);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));

        // And
        String error${domainCamelCase} = IntegrationTestUtil.getErrorMessage(responseEntity);
        String expectedError${domainCamelCase} = "The ${domain} " + NON_EXISTING_DOMAIN_ID + " was not found";

        assertThat(error${domainCamelCase}, is(equalTo(expectedError${domainCamelCase})));
    }

    @Test
    public void get${domainCamelCase}sShouldNotReturn${domainCamelCase}AfterDeletingIt() {

        // Given
        ResponseEntity<${domainCamelCase}sDto> responseEntity = ${domain}TestClient.get${domainCamelCase}s();
        ${domainCamelCase}sDto ${domain}sBeforeDeleting = responseEntity.getBody();

        // When
        ${domain}TestClient.delete${domainCamelCase}(ACTIVE_DOMAIN_ID);

        // Then
        responseEntity = ${domain}TestClient.get${domainCamelCase}s();
        ${domainCamelCase}sDto ${domain}sAfterDeleting = responseEntity.getBody();

        assertTrue(contains${domainCamelCase}WithId(${domain}sBeforeDeleting, ACTIVE_DOMAIN_ID));
        assertFalse(contains${domainCamelCase}WithId(${domain}sAfterDeleting, ACTIVE_DOMAIN_ID));
    }

    private boolean contains${domainCamelCase}WithId(${domainCamelCase}sDto ${domain}sDto, Long ${domain}Id) {

        return ${domain}sDto.get${domainCamelCase}s().stream()
                .anyMatch(${domain} -> ${domain}.getId().equals(${domain}Id));
    }
}
