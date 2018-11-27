package com.rafaborrego.message.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * It could be just a string instead of a DTO but this allows to add more fields in the future
 */
@Getter
@Setter
public class MessageInputDto {

    @NotBlank
    @ApiModelProperty(notes = "The message content")
    private String content;
}
