package com.rafaborrego.message.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;

import java.time.LocalDateTime;

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


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreationTimestamp() {
        return creationTimestamp;
    }

    public void setCreationTimestamp(LocalDateTime creationTimestamp) {
        this.creationTimestamp = creationTimestamp;
    }

    public LocalDateTime getLastUpdateTimestamp() {
        return lastUpdateTimestamp;
    }

    public void setLastUpdateTimestamp(LocalDateTime lastUpdateTimestamp) {
        this.lastUpdateTimestamp = lastUpdateTimestamp;
    }

    public boolean isDeleted() {
        return deleted;
    }

    public void setDeleted(boolean deleted) {
        this.deleted = deleted;
    }
}
