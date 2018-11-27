package ${groupId}.${domain}.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * We could return just the list of ${domain}s instead but this allows to add in the future pagination data (number of pages, etc.)
 * without breaking the existing consumers
 */
@Getter
@Setter
public class ${domainCamelCase}sDto {

    @ApiModelProperty(notes = "The list of ${domain}s")
    private List<${domainCamelCase}OutputDto> ${domain}s;
}
