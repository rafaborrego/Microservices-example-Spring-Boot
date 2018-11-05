package ${groupId}.${domain}.repository;

import ${groupId}.${domain}.entity.${domainCamelCase};

import java.util.Optional;
import org.springframework.data.repository.CrudRepository;

public interface ${domainCamelCase}Repository extends CrudRepository<${domainCamelCase}, Long> {

    Optional<${domainCamelCase}> findById(Long id);
}
