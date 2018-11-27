package ${groupId}.${domain}.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class ${domainCamelCase}OutputDto {

    private static final String DATETIME_DISPLAY_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";

    @ApiModelProperty(notes = "The ${domain} id")
    private Long id;
    
    @ApiModelProperty(notes = "When the ${domain} was created")
    @JsonFormat(pattern = DATETIME_DISPLAY_FORMAT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime creationTimestamp;

    @ApiModelProperty(notes = "The last time the ${domain} was modified")
    @JsonFormat(pattern = DATETIME_DISPLAY_FORMAT)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime lastUpdateTimestamp;

    @ApiModelProperty(notes = "If the ${domain} is deleted")
    private boolean deleted;

    // Sample field so we can do some validations on the tests
    @ApiModelProperty(notes = "The ${domain} content")
    private String content;
}
