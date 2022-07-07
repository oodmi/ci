package org.oodmi.model.external.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitInfoExternal {
    private CommitAuthorExternal author;
    private String message;

    public CommitAuthorExternal getAuthor() {
        return this.author;
    }

    public void setAuthor(CommitAuthorExternal author) {
        this.author = author;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}