package ${groupId}.${domain}.integration;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import static junit.framework.TestCase.assertTrue;
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
        ResponseEntity<String> responseEntity = ${domain}TestClient.get${domainCamelCase}();

        // Then
        assertThat(responseEntity.getStatusCode(), is(equalTo(HttpStatus.OK)));

        // And

        String generated${domainCamelCase} = responseEntity.getBody();

        assertTrue(generated${domainCamelCase}.matches());
    }
}
