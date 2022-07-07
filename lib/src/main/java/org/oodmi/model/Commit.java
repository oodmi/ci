package org.oodmi.model;

import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;

public final class Commit {

    private String sha;
    private String message;
    private LocalDateTime date;
    private String author;
    private String email;

    @NotNull
    public String getSha() {
        return sha;
    }

    public Commit setSha(@NotNull String sha) {
        this.sha = sha;
        return this;
    }

    @NotNull
    public String getMessage() {
        return message;
    }

    public Commit setMessage(@NotNull String message) {
        this.message = message;
        return this;
    }

    @NotNull
    public LocalDateTime getDate() {
        return date;
    }

    public Commit setDate(@NotNull LocalDateTime date) {
        this.date = date;
        return this;
    }

    @NotNull
    public String getAuthor() {
        return author;
    }

    public Commit setAuthor(@NotNull String author) {
        this.author = author;
        return this;
    }

    @NotNull
    public String getEmail() {
        return email;
    }

    public Commit setEmail(@NotNull String email) {
        this.email = email;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Commit commit = (Commit) o;

        if (!Objects.equals(sha, commit.sha)) return false;
        return Objects.equals(email, commit.email);
    }

    @Override
    public int hashCode() {
        int result = sha != null ? sha.hashCode() : 0;
        result = 31 * result + (email != null ? email.hashCode() : 0);
        return result;
    }
}
