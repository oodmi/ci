package org.oodmi.model;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public final class Branch {
    private String name;
    private String sha;

    @NotNull
    public String getName() {
        return name;
    }

    public Branch setName(@NotNull String name) {
        this.name = name;
        return this;
    }

    @NotNull
    public String getSha() {
        return sha;
    }

    public Branch setSha(@NotNull String sha) {
        this.sha = sha;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Branch branch = (Branch) o;

        if (!Objects.equals(name, branch.name)) return false;
        return Objects.equals(sha, branch.sha);
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (sha != null ? sha.hashCode() : 0);
        return result;
    }
}
