package ${groupId}.${domain}.service;

import ${groupId}.${domain}.dto.${domainCamelCase}InputDto;
import ${groupId}.${domain}.entity.${domainCamelCase};

public interface ${domainCamelCase}Service {

    ${domainCamelCase} get${domainCamelCase}ById(Long id);

    Iterable<${domainCamelCase}> get${domainCamelCase}s();

    ${domainCamelCase} create${domainCamelCase}(${domainCamelCase}InputDto ${domain}Data);

    ${domainCamelCase} modify${domainCamelCase}(Long ${domain}Id, ${domainCamelCase}InputDto ${domain}Data);

    void delete${domainCamelCase}(Long ${domain}Id);
}
