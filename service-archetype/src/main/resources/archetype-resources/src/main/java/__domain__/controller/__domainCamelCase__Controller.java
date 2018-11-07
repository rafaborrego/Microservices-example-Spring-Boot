package ${groupId}.${domain}.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ${groupId}.${domain}.entity.${domainCamelCase};
import ${groupId}.${domain}.dto.${domainCamelCase}InputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}OutputDto;
import ${groupId}.${domain}.dto.${domainCamelCase}sDto;

@Api(value="${artifactId}", description="${domainCamelCase} operations")
public interface ${domainCamelCase}Controller {

    @ApiOperation(value = "Gets a ${domain}")
    @ApiResponses(value = { @ApiResponse(code = 200, message = "${domainCamelCase} returned successfully"),
                            @ApiResponse(code = 404, message = "${domainCamelCase} not found")})
    ${domainCamelCase}OutputDto get${domainCamelCase}ById(Long id);

    @ApiOperation(value = "Get ${domain}s")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "${domainCamelCase}s returned successfully")
    })
    ${domainCamelCase}sDto get${domainCamelCase}s();

    @ApiOperation(value = "Create a ${domain}")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "${domainCamelCase} added successfully"),
            @ApiResponse(code = 400, message = "The ${domain} has invalid content")
    })
    ${domainCamelCase}OutputDto create${domainCamelCase}(${domainCamelCase}InputDto ${domain}Data);
    
    
    @ApiOperation(value = "Modify a ${domain}")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "${domainCamelCase} modified successfully"),
            @ApiResponse(code = 404, message = "${domainCamelCase} not found"),
            @ApiResponse(code = 400, message = "The ${domain} has invalid content")
    })
    ${domainCamelCase}OutputDto modify${domainCamelCase}(Long ${domain}Id, ${domainCamelCase}InputDto ${domain}Data);
    
    @ApiOperation(value = "Delete a ${domain}")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "${domainCamelCase} deleted successfully"),
            @ApiResponse(code = 404, message = "${domainCamelCase} not found")
    })
    void delete${domainCamelCase}(Long ${domain}Id);
}

