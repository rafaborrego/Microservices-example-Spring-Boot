package ${groupId}.${domain}.integration;

import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}sDto;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.cloud.contract.stubrunner.spring.AutoConfigureStubRunner;
import org.springframework.cloud.contract.stubrunner.spring.StubRunnerProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Sql(executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD, scripts = {"classpath:cleanUp.sql", "classpath:test${domainCamelCase}sInsertionScript.sql"})
public class ${domainCamelCase}CreationIntegrationTest {

    private final static String SAMPLE_CONTENT = "Sample content";

    @Autowired
    private ${domainCamelCase}TestClient ${domain}TestClient;

    @Test
    public void create${domainCamelCase}ShouldCreate${domainCamelCase}WithExpectedContentAndStatusCreated() {

        // When
        ResponseEntity<Map> responseEntity = ${domain}TestClient.create${domainCamelCase}(SAMPLE_CONTENT);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.CREATED)));

        // And
        ${domainCamelCase}OutputDto created${domainCamelCase} = IntegrationTestUtil.convertMapTo${domainCamelCase}Dto(responseEntity.getBody());
        assertThat(created${domainCamelCase}.getContent(), is(equalTo(SAMPLE_CONTENT)));
        assertThat(created${domainCamelCase}.getCreationTimestamp(), is(notNullValue()));
        assertThat(created${domainCamelCase}.getLastUpdateTimestamp(), is(notNullValue()));
        assertThat(created${domainCamelCase}.getLastUpdateTimestamp(), is(equalTo(created${domainCamelCase}.getCreationTimestamp())));
    }
    
    @Test
    public void get${domainCamelCase}sShouldReturn${domainCamelCase}AfterCreatingIt() {

        // Given
        ResponseEntity<${domainCamelCase}sDto> responseEntity = ${domain}TestClient.get${domainCamelCase}s();
        List<${domainCamelCase}OutputDto> ${domain}sBefore = responseEntity.getBody().get${domainCamelCase}s();

        // When
        ${domain}TestClient.create${domainCamelCase}(SAMPLE_CONTENT);

        // Then
        responseEntity = ${domain}TestClient.get${domainCamelCase}s();
        List<${domainCamelCase}OutputDto> ${domain}sAfter = responseEntity.getBody().get${domainCamelCase}s();

        assertThat(${domain}sAfter.size(), is(equalTo(${domain}sBefore.size() + 1)));
        assertThat(SAMPLE_CONTENT, is(equalTo(${domain}sAfter.get(0).getContent())));
    }
}
