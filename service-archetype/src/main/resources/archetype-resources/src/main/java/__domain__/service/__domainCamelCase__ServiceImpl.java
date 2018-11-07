package ${groupId}.${domain}.service;

import ${groupId}.${domain}.dto.${domainCamelCase}InputDto;
import ${groupId}.${domain}.repository.${domainCamelCase}Repository;
import ${groupId}.${domain}.entity.${domainCamelCase};
import ${groupId}.${domain}.exception.${domainCamelCase}NotFoundException;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
public class ${domainCamelCase}ServiceImpl implements ${domainCamelCase}Service {

    private final ${domainCamelCase}Repository ${domain}Repository;

    public ${domainCamelCase}ServiceImpl(${domainCamelCase}Repository ${domain}Repository) {
        this.${domain}Repository = ${domain}Repository;
    }

    @Override
    public ${domainCamelCase} get${domainCamelCase}ById (Long id) {

        return ${domain}Repository.findById(id)
            .orElseThrow(() -> new ${domainCamelCase}NotFoundException(id));
    }

    @Override
    @Transactional(readOnly = true)
    public Iterable<${domainCamelCase}> get${domainCamelCase}s() {

        return ${domain}Repository.findAll();
    }

    @Override
    public ${domainCamelCase} create${domainCamelCase}(${domainCamelCase}InputDto ${domain}Data) {

        LocalDateTime currentDateTime = LocalDateTime.now();

        ${domainCamelCase} ${domain} = new ${domainCamelCase}();
        ${domain}.setCreationTimestamp(currentDateTime);
        ${domain}.setLastUpdateTimestamp(currentDateTime);
        // Add the content of ${domain}Data to the entity

        ${domain}Repository.save(${domain});

        return ${domain};
    }

    @Override
    public ${domainCamelCase} modify${domainCamelCase}(Long ${domain}Id, ${domainCamelCase}InputDto ${domain}Data) {

        ${domainCamelCase} ${domain} = ${domain}Repository.findById(${domain}Id)
            .orElseThrow(() -> new ${domainCamelCase}NotFoundException(${domain}Id));

        return modify${domainCamelCase}(${domain}, ${domain}Data);
    }

    private ${domainCamelCase} modify${domainCamelCase}(${domainCamelCase} ${domain}, ${domainCamelCase}InputDto ${domain}Data) {
        
        ${domain}.setLastUpdateTimestamp(LocalDateTime.now());
        // Modify the entity with the content of ${domain}Data

        return ${domain}Repository.save(${domain});
    }

    /**
    * It could throw a not found error if the ${domain} is already deleted.
    * However I think that it is better to make the deletes idempotent
    */
    @Override
    public void delete${domainCamelCase}(Long ${domain}Id) {

        ${domainCamelCase} ${domain} = ${domain}Repository.findById(${domain}Id)
            .orElseThrow(() -> new ${domainCamelCase}NotFoundException(${domain}Id));

        softDelete${domainCamelCase}(${domain});
    }

    private void softDelete${domainCamelCase}(${domainCamelCase} ${domain}) {

        if (!${domain}.isDeleted()) {

            ${domain}.setDeleted(true);
            ${domain}.setLastUpdateTimestamp(LocalDateTime.now());
    
            ${domain}Repository.save(${domain});
        }
    }
}
