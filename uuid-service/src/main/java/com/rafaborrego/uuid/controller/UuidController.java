package com.rafaborrego.uuid.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="uuid-service", description="UUID operations")
public interface UuidController {

    @ApiOperation(value = "Generates a UUID")
    @ApiResponses(value = @ApiResponse(code = 200, message = "UUID generated successfully"))
    String generateUuid();
}
