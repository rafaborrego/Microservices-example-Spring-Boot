package com.rafaborrego.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class MessageOutputDto {

    private static final String DATETIME_DISPLAY_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @ApiModelProperty(notes = "The message id")
    private Long id;

    @ApiModelProperty(notes = "The message uuid")
    private String uuid;

    @ApiModelProperty(notes = "The message content")
    private String content;

    @ApiModelProperty(notes = "When the message was created")
    @JsonFormat(pattern = DATETIME_DISPLAY_FORMAT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationTimestamp;

    @ApiModelProperty(notes = "The last time the message was modified")
    @JsonFormat(pattern = DATETIME_DISPLAY_FORMAT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateTimestamp;

    @ApiModelProperty(notes = "If the message is deleted")
    private boolean deleted;
}
