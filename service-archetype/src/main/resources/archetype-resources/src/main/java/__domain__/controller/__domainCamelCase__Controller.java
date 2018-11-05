package ${groupId}.${domain}.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import ${groupId}.${domain}.entity.${domainCamelCase};

@Api(value="${artifactId}", description="${domainCamelCase} operations")
public interface ${domainCamelCase}Controller {

    @ApiOperation(value = "Gets ${domain}s")
    @ApiResponses(value = @ApiResponse(code = 200, message = "${domain} returned successfully"))
    ${domainCamelCase} get${domainCamelCase}(Long id);
}
