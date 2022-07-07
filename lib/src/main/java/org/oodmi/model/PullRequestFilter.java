package org.oodmi.model;

import org.jetbrains.annotations.NotNull;

/**
 * Filter for pull request.
 */
public class PullRequestFilter {

    private PullState state;

    private String author;

    private String into;

    private String from;

    public PullRequestFilter() {
        this.state = PullState.opened;
    }

    @NotNull
    public PullRequestFilter byState(@NotNull PullState state) {
        this.state = state;
        return this;
    }

    @NotNull
    public PullRequestFilter byIntoBranch(@NotNull String into) {
        this.into = into;
        return this;
    }

    @NotNull
    public PullRequestFilter byFromBranchAndAuthor(@NotNull String from, @NotNull String author) {
        this.from = from;
        this.author = author;
        return this;
    }

    public PullState getState() {
        return state;
    }

    public String getAuthor() {
        return author;
    }

    public String getInto() {
        return into;
    }

    public String getFrom() {
        return from;
    }
}