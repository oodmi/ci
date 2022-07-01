package org.oodmi.model.request;

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
    private String head;
    @NotNull
    private String base;

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

    public String getHead() {
        return head;
    }

    public OpenPullRequest setHead(String head) {
        this.head = head;
        return this;
    }

    public String getBase() {
        return base;
    }

    public OpenPullRequest setBase(String base) {
        this.base = base;
        return this;
    }
}