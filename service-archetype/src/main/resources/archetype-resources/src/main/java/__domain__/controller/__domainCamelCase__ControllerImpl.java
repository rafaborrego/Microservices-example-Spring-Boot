package ${groupId}.${domain}.controller;

import ${groupId}.${domain}.dto.${domainCamelCase}InputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}sDto;
import ${groupId}.${domain}.entity.Invoice;
import ${groupId}.${domain}.mapper.BeanMapper;
import ${groupId}.${domain}.service.${domainCamelCase}Service;
import io.swagger.annotations.ApiParam;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = ${groupId}.${domain}.controller.Endpoint.BASE_DOMAIN_URL, produces = MediaType.APPLICATION_JSON_VALUE)
public class ${domainCamelCase}ControllerImpl implements ${domainCamelCase}Controller {

    private final BeanMapper beanMapper;

    private final ${domainCamelCase}Service ${domain}Service;

    public ${domainCamelCase}ControllerImpl(${domainCamelCase}Service ${domain}Service, BeanMapper beanMapper) {
        this.${domain}Service = ${domain}Service;
        this.beanMapper = beanMapper;
    }

    @Override
    @GetMapping(Endpoint.PATH_DOMAIN_ID)
    public ${domainCamelCase}OutputDto get${domainCamelCase}ById(Long id) {

        return beanMapper.map(${domain}Service.get${domainCamelCase}ById(id), ${domainCamelCase}OutputDto.class);
    }

    @Override
    @GetMapping
    public ${domainCamelCase}sDto get${domainCamelCase}s() {

        Iterable<${domainCamelCase}> ${domain}s = ${domain}Service.get${domainCamelCase}s();

        return convert${domainCamelCase}sTo${domainCamelCase}sDto(${domain}s);
    }

    @Override
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ${domainCamelCase}OutputDto create${domainCamelCase}(@ApiParam("${domainCamelCase} data") @Validated @RequestBody ${domainCamelCase}InputDto ${domain}Data) {

        ${domainCamelCase} ${domain} = ${domain}Service.create${domainCamelCase}(${domain}Data);

        return beanMapper.map(${domain}, ${domainCamelCase}OutputDto.class);
    }

    @Override
    @PutMapping(Endpoint.PATH_DOMAIN_ID)
    public ${domainCamelCase}OutputDto modify${domainCamelCase}(@ApiParam("${domainCamelCase} id") @PathVariable Long ${domain}Id,
    @ApiParam("${domainCamelCase} data") @Validated @RequestBody ${domainCamelCase}InputDto ${domain}Data) {

        ${domainCamelCase} ${domain} = ${domain}Service.modify${domainCamelCase}(${domain}Id, ${domain}Data);

        return beanMapper.map(${domain}, ${domainCamelCase}OutputDto.class);
    }

    @Override
    @DeleteMapping(Endpoint.PATH_DOMAIN_ID)
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete${domainCamelCase}(@ApiParam("${domainCamelCase} id") @PathVariable Long ${domain}Id) {

        ${domain}Service.delete${domainCamelCase}(${domain}Id);
    }

    private ${domainCamelCase}sDto convert${domainCamelCase}sTo${domainCamelCase}sDto(Iterable<${domainCamelCase}> ${domain}s) {

        Iterable<${domainCamelCase}OutputDto> converted${domainCamelCase}s = beanMapper.mapAsList(${domain}s, ${domainCamelCase}OutputDto.class);

        ${domainCamelCase}sDto ${domain}sDto = new ${domainCamelCase}sDto();
        ${domain}sDto.set${domainCamelCase}s(converted${domainCamelCase}s);

        return ${domain}sDto;
    }
}
