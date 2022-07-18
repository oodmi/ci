package org.oodmi.exceptions;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.StringJoiner;

public class GithubException extends RuntimeException {

    @Nullable
    private final Integer httpCode;
    @Nullable
    private final String message;
    @Nullable
    private final String documentation;
    @NotNull
    private final List<String> details;

    public GithubException(@Nullable Integer httpCode,
                           @Nullable String message,
                           @Nullable String documentation,
                           @NotNull List<String> details) {
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
                .add("message=" + message)
                .add("documentation=" + documentation)
                .add("details=" + String.join(",", details))
                .toString();
    }
}
