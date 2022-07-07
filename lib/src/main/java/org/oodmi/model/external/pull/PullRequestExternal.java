package org.oodmi.model.external.pull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.oodmi.model.PullRequest;

import java.time.LocalDateTime;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PullRequestExternal {
    private Long number;
    private String state;
    private String title;
    private UserExternal user;
    private String body;
    @JsonProperty("created_at")
    private LocalDateTime createdAt;
    @JsonProperty("updated_at")
    private LocalDateTime updatedAt;
    private HeadExternal head;
    private BaseExternal base;

    public PullRequest map() {
        return new PullRequest()
                .setNumber(number)
                .setState(state)
                .setName(user.getName())
                .setLogin(user.getLogin())
                .setEmail(user.getEmail())
                .setInto(base.getRef())
                .setFrom(head.getRef())
                .setTitle(title)
                .setSummary(body)
                .setCreatedAt(createdAt)
                .setUpdatedAt(updatedAt);
    }

    public Long getNumber() {
        return this.number;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getTitle() {
        return this.title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public UserExternal getUser() {
        return this.user;
    }

    public void setUser(UserExternal user) {
        this.user = user;
    }

    public String getBody() {
        return this.body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public LocalDateTime getCreatedAt() {
        return this.createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return this.updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public HeadExternal getHead() {
        return this.head;
    }

    public void setHead(HeadExternal head) {
        this.head = head;
    }

    public BaseExternal getBase() {
        return this.base;
    }

    public void setBase(BaseExternal base) {
        this.base = base;
    }
}