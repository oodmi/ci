package org.oodmi.exceptions;

public class GithubException extends RuntimeException {

    private final Integer httpCode;
    private final String message;
    private final String documentation;

    public GithubException(Integer httpCode, String message, String documentation) {
        super(message);
        this.httpCode = httpCode;
        this.message = message;
        this.documentation = documentation;
    }

    public Integer getHttpCode() {
        return httpCode;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public String getDocumentation() {
        return documentation;
    }
}
