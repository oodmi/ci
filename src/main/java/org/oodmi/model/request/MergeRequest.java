package org.oodmi.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MergeRequest {
    private String sha;
    private boolean merged;
    @Schema(example = "Pull Request successfully merged")
    private String message;
}
