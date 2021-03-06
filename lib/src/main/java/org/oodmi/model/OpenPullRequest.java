package org.oodmi.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenPullRequest {
    @NotNull
    private String title;
    @Nullable
    private String body;
    @NotNull
    private String from;
    @NotNull
    private String into;

    public String getTitle() {
        return title;
    }

    public OpenPullRequest setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getBody() {
        return body;
    }

    public OpenPullRequest setBody(String body) {
        this.body = body;
        return this;
    }

    public String getFrom() {
        return from;
    }

    public OpenPullRequest setFrom(String from) {
        this.from = from;
        return this;
    }

    public String getInto() {
        return into;
    }

    public OpenPullRequest setInto(String into) {
        this.into = into;
        return this;
    }
}