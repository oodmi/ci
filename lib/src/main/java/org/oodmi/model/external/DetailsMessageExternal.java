package org.oodmi.model.external;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class DetailsMessageExternal {
    private String resource;
    private String code;
    private String field;
    private String message;

    public String getResource() {
        return resource;
    }

    public String getCode() {
        return code;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }
}