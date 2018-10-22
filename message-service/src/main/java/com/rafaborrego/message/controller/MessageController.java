package com.rafaborrego.message.controller;

import com.rafaborrego.message.dto.MessageOutputDto;
import com.rafaborrego.message.dto.MessageInputDto;
import com.rafaborrego.message.dto.MessagesDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="message-service", description="Message management operations")
public interface MessageController {

    @ApiOperation(value = "Add a message")
    @ApiResponses(value = {
            @ApiResponse(code = 201, message = "Message added successfully"),
            @ApiResponse(code = 400, message = "The message has invalid content"),
            @ApiResponse(code = 424, message = "The message cannot be created due to a failed dependency. E.g. error obtaining the uuid")
    })
    MessageOutputDto addMessage(MessageInputDto messageData);


    @ApiOperation(value = "Modify a message (only within 10 seconds after it is created)")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Message modified successfully"),
            @ApiResponse(code = 403, message = "The message cannot be modified"),
            @ApiResponse(code = 404, message = "Message not found"),
            @ApiResponse(code = 400, message = "The message has invalid content")
    })
    MessageOutputDto modifyMessage(Long messageId, MessageInputDto messageData);


    @ApiOperation(value = "Get messages")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Messages returned successfully")
    })
    MessagesDto getMessages();


    @ApiOperation(value = "Delete a message (if it is older than 2 minutes)")
    @ApiResponses(value = {
            @ApiResponse(code = 204, message = "Message deleted successfully"),
            @ApiResponse(code = 403, message = "The message cannot be deleted now"),
            @ApiResponse(code = 404, message = "Message not found")
    })
    void deleteMessage(Long messageId);
}
