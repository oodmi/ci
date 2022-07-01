package org.oodmi.model.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MergeResponse {
    private String sha;
    private boolean merged;
    private String message;
}
