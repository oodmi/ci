package org.oodmi.client;

import okhttp3.Request;
import okhttp3.Response;

import java.util.concurrent.CompletableFuture;

/**
 * Client for operations with pul token.
 */
public class TokenClient {
    private final GitHubHttpExecutor gitHubHttpExecutor;

    public TokenClient(String auth, String name, String project) {
        this.gitHubHttpExecutor = new GitHubHttpExecutor(auth, name, project);
    }

    /**
     * Check your token
     *
     * @return true - valid, false - invalid
     */
    public CompletableFuture<Boolean> check() {
        return gitHubHttpExecutor.newCall(new Request.Builder().get())
                .thenApply(Response::isSuccessful);
    }
}
