package org.oodmi.model;

import org.jetbrains.annotations.NotNull;

public record GithubSettings(@NotNull String token,
                             @NotNull String name,
                             @NotNull String project,
                             @NotNull Integer retryLimit) {
}
