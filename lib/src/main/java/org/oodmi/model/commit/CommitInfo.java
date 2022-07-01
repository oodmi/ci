package org.oodmi.model.commit;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommitInfo {
    private CommitAuthor author;
    private String message;

    public CommitAuthor getAuthor() {
        return this.author;
    }

    public void setAuthor(CommitAuthor author) {
        this.author = author;
    }

    public String getMessage() {
        return this.message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}