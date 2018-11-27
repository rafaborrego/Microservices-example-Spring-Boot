package ${groupId}.${domain}.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ${domainCamelCase}InputDto {

    // Sample field so we can do some validations on the tests
    @ApiModelProperty(notes = "The ${domain} content")
    private String content;
}
