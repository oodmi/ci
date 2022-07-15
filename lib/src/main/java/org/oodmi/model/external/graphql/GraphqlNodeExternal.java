package org.oodmi.model.external.graphql;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GraphqlNodeExternal {
    private Long number;
    private String state;
    private String title;
    private String body;
    private String headRefName;
    private String baseRefName;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private GraphqlAuthorExternal author;

    public Long getNumber() {
        return number;
    }

    public String getState() {
        return state;
    }

    public String getTitle() {
        return title;
    }

    public String getBody() {
        return body;
    }

    public String getHeadRefName() {
        return headRefName;
    }

    public String getBaseRefName() {
        return baseRefName;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public GraphqlAuthorExternal getAuthor() {
        return author;
    }
}