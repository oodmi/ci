package org.oodmi.client;

import okhttp3.Request;
import okhttp3.Response;
import org.oodmi.model.GithubSettings;

import java.util.concurrent.CompletableFuture;

/**
 * Client for operations with pull token.
 */
public final class TokenClient {
    private GitHubHttpExecutor gitHubHttpExecutor;

    public TokenClient(GithubSettings properties) {
        this.gitHubHttpExecutor = new GitHubHttpExecutor(properties);
    }

    TokenClient() {
    }

    void setGitHubHttpExecutor(GitHubHttpExecutor gitHubHttpExecutor) {
        this.gitHubHttpExecutor = gitHubHttpExecutor;
    }

    /**
     * Check your token
     *
     * @return true - valid, false - invalid
     */
    public CompletableFuture<Boolean> check() {
        return gitHubHttpExecutor.newCall(new Request.Builder().get(), "")
                .thenApply(Response::isSuccessful);
    }
}
