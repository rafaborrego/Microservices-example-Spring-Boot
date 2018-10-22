package com.rafaborrego.message.dto;

import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.NotBlank;

/**
 * It could be just a string instead of a DTO but this allows to add more fields in the future
 */
public class MessageInputDto {

    @NotBlank
    @ApiModelProperty(notes = "The message content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
