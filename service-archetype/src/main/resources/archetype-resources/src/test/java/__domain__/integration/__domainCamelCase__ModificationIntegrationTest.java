package ${groupId}.${domain}.integration;

import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}sDto;
import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Map;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:test${domainCamelCase}sInsertionScript.sql"})
public class ${domainCamelCase}ModificationIntegrationTest {

    private static final Long DELETED_DOMAIN_ID = 26L;
    private static final Long NON_EXISTING_DOMAIN_ID = 100L;

    @Autowired
    private ${domainCamelCase}TestClient ${domain}TestClient;

    @Test
    public void modify${domainCamelCase}ShouldReturnModified${domainCamelCase}() {

        // Given
        String contentBefore = "Before";
        String contentAfter = "After";

        ResponseEntity<Map> creationResponseEntity = ${domain}TestClient.create${domainCamelCase}(contentBefore);
        ${domainCamelCase}OutputDto ${domain} = IntegrationTestUtil.convertMapTo${domainCamelCase}Dto(creationResponseEntity.getBody());
        
        LocalDateTime creationTimeBefore = ${domain}.getCreationTimestamp();
        LocalDateTime lastUpdatedTimeBefore = ${domain}.getLastUpdateTimestamp();

        // When
        ResponseEntity<Map> modificationResponseEntity = ${domain}TestClient.modify${domainCamelCase}(${domain}.getId(), contentAfter);

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And
        ${domainCamelCase}OutputDto ${domain}Dto = IntegrationTestUtil.convertMapTo${domainCamelCase}Dto(modificationResponseEntity.getBody());
        String ${domain}ContentAfter = ${domain}Dto.getContent();
        LocalDateTime creationTimeAfter = ${domain}Dto.getCreationTimestamp();
        LocalDateTime lastUpdatedTimeAfter = ${domain}Dto.getLastUpdateTimestamp();

        assertThat(${domain}ContentAfter, is(equalTo(contentAfter)));
        assertThat(creationTimeAfter, is(equalTo(creationTimeBefore)));
        assertThat(lastUpdatedTimeAfter, greaterThan(lastUpdatedTimeBefore));
    }
    
    @Test
    public void modify${domainCamelCase}ShouldReturnErrorWhen${domainCamelCase}DoesNotExist() {

        // When
        ResponseEntity<Map> modificationResponseEntity = ${domain}TestClient.modify${domainCamelCase}(NON_EXISTING_DOMAIN_ID, "content");

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));

        // And
        String error${domainCamelCase} = IntegrationTestUtil.getErrorMessage(modificationResponseEntity);
        String expectedError${domainCamelCase} = "The ${domain} " + NON_EXISTING_DOMAIN_ID + " was not found";

        assertThat(error${domainCamelCase}, is(equalTo(expectedError${domainCamelCase})));
    }

    @Test
    public void modify${domainCamelCase}ShouldReturnErrorWhen${domainCamelCase}IsDeleted() {

        // When
        ResponseEntity<Map> modificationResponseEntity = ${domain}TestClient.modify${domainCamelCase}(DELETED_DOMAIN_ID, "content");

        // Then
        assertThat(modificationResponseEntity.getStatusCode(), is(equalTo(HttpStatus.NOT_FOUND)));

        // And
        String error${domainCamelCase} = IntegrationTestUtil.getErrorMessage(modificationResponseEntity);
        String expectedError${domainCamelCase} = "The ${domain} " + DELETED_DOMAIN_ID + " was not found";

        assertThat(error${domainCamelCase}, is(equalTo(expectedError${domainCamelCase})));
    }

    @Test
    public void get${domainCamelCase}sShouldReturnModified${domainCamelCase}() {

        // Given
        String ${domain}Before = "Content before";
        ResponseEntity<Map> created${domainCamelCase}sResponseEntity = ${domain}TestClient.create${domainCamelCase}(${domain}Before);
        ${domainCamelCase}OutputDto ${domain} = IntegrationTestUtil.convertMapTo${domainCamelCase}Dto(created${domainCamelCase}sResponseEntity.getBody());

        // When
        String newContent = "New content";
        ${domain}TestClient.modify${domainCamelCase}(${domain}.getId(), newContent);

        // Then
        ResponseEntity<${domainCamelCase}sDto> ${domain}sAfterResponseEntity = ${domain}TestClient.get${domainCamelCase}s();
        String ${domain}ContentAfter = find${domainCamelCase}ContentById(${domain}sAfterResponseEntity.getBody(), ${domain}.getId());

        assertThat(${domain}ContentAfter, is(equalTo(newContent)));
    }

    private String find${domainCamelCase}ContentById(${domainCamelCase}sDto ${domain}sDto, Long ${domain}Id) {

        return ${domain}sDto.get${domainCamelCase}s().stream()
                .filter(${domain} -> ${domain}Id.equals(${domain}.getId()))
                .findFirst().get()
                .getContent();
    }
}
