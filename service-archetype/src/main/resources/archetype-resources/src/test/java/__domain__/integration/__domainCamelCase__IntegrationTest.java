package ${groupId}.${domain}.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class ${domainCamelCase}IntegrationTest {

    @Autowired
    private ${domainCamelCase}TestClient ${domain}TestClient;

    @Test
    public void generate${domainCamelCase}ShouldReturn${domainCamelCase}() {

        // When
        ResponseEntity<${domainCamelCase}OutputDto> responseEntity = ${domain}TestClient.get${domainCamelCase}ById(1L);

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And
        ${domainCamelCase}OutputDto expected${domainCamelCase} = null;
        ${domainCamelCase}OutputDto generated${domainCamelCase} = responseEntity.getBody();

        assertThat(generated${domainCamelCase}, is(equalTo(expected${domainCamelCase})));
    }
}
