package org.oodmi.model.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponseExternal {
    private String message;
    @JsonProperty("documentation_url")
    private String documentationUrl;
    private final List<DetailsMessageExternal> errors = new ArrayList<>();

    public String getMessage() {
        return message;
    }

    public String getDocumentationUrl() {
        return documentationUrl;
    }

    public List<DetailsMessageExternal> getErrors() {
        return errors;
    }
}
