package ${groupId}.${domain}.controller;

import ${groupId}.${domain}.entity.${domainCamelCase};
import ${groupId}.${domain}.service.${domainCamelCase}Service;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = ${groupId}.${domain}.controller.Endpoint.BASE_DOMAIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ${domainCamelCase}ControllerImpl implements ${domainCamelCase}Controller {

    private final ${domainCamelCase}Service ${domain}Service;

    public ${domainCamelCase}ControllerImpl(${domainCamelCase}Service ${domain}Service) {
        this.${domain}Service = ${domain}Service;
    }

    @Override
    @GetMapping
    public ${domainCamelCase} get${domainCamelCase}(Long id) {

        return ${domain}Service.get${domainCamelCase}(id);
    }
}
