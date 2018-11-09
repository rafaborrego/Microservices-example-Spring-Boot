package ${groupId}.${domain}.repository;

import ${groupId}.${domain}.entity.${domainCamelCase};

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ${domainCamelCase}Repository extends CrudRepository<${domainCamelCase}, Long> {

    @Query("select ${domain} " +
            "from #{#entityName} ${domain} " +
            "where ${domain}.deleted=false " +
            "order by ${domain}.creationTimestamp desc")
    List<${domainCamelCase}> findNonDeleted${domainCamelCase}sOrderedByCreationTimestamp();
}
