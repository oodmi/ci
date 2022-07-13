package org.oodmi.exceptions;

import java.util.List;
import java.util.StringJoiner;

public class GithubException extends RuntimeException {

    private final Integer httpCode;
    private final String message;
    private final String documentation;

    private final List<String> details;

    public GithubException(Integer httpCode, String message, String documentation, List<String> details) {
        super(message);
        this.httpCode = httpCode;
        this.message = message;
        this.documentation = documentation;
        this.details = details;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    @Override
    public String getMessage() {
        return toString();
    }

    public String getDocumentation() {
        return documentation;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GithubException.class.getSimpleName() + "[", "]")
                .add("httpCode=" + httpCode)
                .add("message='" + message + "'")
                .add("documentation='" + documentation + "'")
                .add("details=" + String.join(",", details))
                .toString();
    }
}
