package ${groupId}.${domain}.dto;

import io.swagger.annotations.ApiModelProperty;

public class ${domainCamelCase}InputDto {

    // Sample field so we can do some validations on the tests
    @ApiModelProperty(notes = "The ${domain} content")
    private String content;

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
