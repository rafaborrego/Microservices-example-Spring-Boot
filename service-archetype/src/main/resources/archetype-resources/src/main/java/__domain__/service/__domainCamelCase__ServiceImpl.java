package ${groupId}.${domain}.service;

import org.springframework.stereotype.Service;
import ${groupId}.${domain}.repository.${domainCamelCase}Repository;
import ${groupId}.${domain}.entity.${domainCamelCase};
import ${groupId}.${domain}.exception.${domainCamelCase}NotFoundException;

@Service
public class ${domainCamelCase}ServiceImpl implements ${domainCamelCase}Service {

    private final ${domainCamelCase}Repository ${domain}Repository;

    public ${domainCamelCase}ServiceImpl(${domainCamelCase}Repository ${domain}Repository) {
        this.${domain}Repository = ${domain}Repository;
    }

    @Override
    public ${domainCamelCase} get${domainCamelCase} (Long id) {

        return ${domain}Repository.findById(id)
            .orElseThrow(() -> new InvoiceNotFoundException(id));
    }
}
