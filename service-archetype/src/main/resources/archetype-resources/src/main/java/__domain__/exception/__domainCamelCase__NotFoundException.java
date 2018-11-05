package ${groupId}.${domain}.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ${domainCamelCase}NotFoundException extends RuntimeException {

    public ${domainCamelCase}NotFoundException(Long messageId) {
        super(String.format("The ${domain} %d was not found", messageId));
    }
}
