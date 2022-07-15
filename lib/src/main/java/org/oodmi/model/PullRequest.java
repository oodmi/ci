package org.oodmi.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public final class PullRequest {
    private Long number;
    private String state;
    private String login;
    private String name;
    private String email;
    private String into;
    private String from;
    private String title;
    private String summary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @NotNull
    public Long getNumber() {
        return number;
    }

    public PullRequest setNumber(@NotNull Long number) {
        this.number = number;
        return this;
    }

    @NotNull
    public String getState() {
        return state;
    }

    public PullRequest setState(@NotNull String state) {
        this.state = state;
        return this;
    }

    @NotNull
    public String getLogin() {
        return login;
    }

    public PullRequest setLogin(@NotNull String login) {
        this.login = login;
        return this;
    }

    @NotNull
    public String getName() {
        return name;
    }

    public PullRequest setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public PullRequest setEmail(@NotNull String email) {
        this.email = email;
        return this;
    }

    @NotNull
    public String getInto() {
        return into;
    }

    public PullRequest setInto(@NotNull String into) {
        this.into = into;
        return this;
    }

    @NotNull
    public String getFrom() {
        return from;
    }

    public PullRequest setFrom(@NotNull String from) {
        this.from = from;
        return this;
    }

    @NotNull
    public String getTitle() {
        return title;
    }

    public PullRequest setTitle(@NotNull String title) {
        this.title = title;
        return this;
    }

    @NotNull
    public String getSummary() {
        return summary;
    }

    public PullRequest setSummary(@NotNull String summary) {
        this.summary = summary;
        return this;
    }

    @NotNull
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public PullRequest setCreatedAt(@NotNull LocalDateTime createdAt) {
        this.createdAt = createdAt;
        return this;
    }

    @NotNull
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public PullRequest setUpdatedAt(@NotNull LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PullRequest that = (PullRequest) o;

        if (!Objects.equals(number, that.number)) return false;
        if (!Objects.equals(state, that.state)) return false;
        if (!Objects.equals(login, that.login)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(email, that.email)) return false;
        if (!Objects.equals(into, that.into)) return false;
        if (!Objects.equals(from, that.from)) return false;
        if (!Objects.equals(title, that.title)) return false;
        if (!Objects.equals(summary, that.summary)) return false;
        if (!Objects.equals(createdAt, that.createdAt)) return false;
        return Objects.equals(updatedAt, that.updatedAt);
    }

    @Override
    public int hashCode() {
        int result = number != null ? number.hashCode() : 0;
        result = 31 * result + (state != null ? state.hashCode() : 0);
        result = 31 * result + (login != null ? login.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (email != null ? email.hashCode() : 0);
        result = 31 * result + (into != null ? into.hashCode() : 0);
        result = 31 * result + (from != null ? from.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (summary != null ? summary.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (updatedAt != null ? updatedAt.hashCode() : 0);
        return result;
    }
}
