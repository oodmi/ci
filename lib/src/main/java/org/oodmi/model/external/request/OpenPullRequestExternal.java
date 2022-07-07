package org.oodmi.model.external.request;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class OpenPullRequestExternal {
    private String title;
    private String body;
    private String head;
    private String base;

    public String getTitle() {
        return title;
    }

    public OpenPullRequestExternal setTitle(@NotNull String title) {
        this.title = title;
        return this;
    }

    @Nullable
    public String getBody() {
        return body;
    }

    public OpenPullRequestExternal setBody(@Nullable String body) {
        this.body = body;
        return this;
    }

    @NotNull
    public String getHead() {
        return head;
    }

    public OpenPullRequestExternal setHead(@NotNull String head) {
        this.head = head;
        return this;
    }

    @NotNull
    public String getBase() {
        return base;
    }

    public OpenPullRequestExternal setBase(@NotNull String base) {
        this.base = base;
        return this;
    }
}