package ${groupId}.${domain}.dto;

import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * We could return just the list of ${domain}s instead but this allows to add in the future pagination data (number of pages, etc.)
 * without breaking the existing consumers
 */
public class ${domainCamelCase}sDto {

    @ApiModelProperty(notes = "The list of ${domain}s")
    private List<${domainCamelCase}OutputDto> ${domain}s;

    public List<${domainCamelCase}OutputDto> get${domainCamelCase}s() {
        return ${domain}s;
    }

    public void set${domainCamelCase}s(List<${domainCamelCase}OutputDto> ${domain}s) {
        this.${domain}s = ${domain}s;
    }
}
