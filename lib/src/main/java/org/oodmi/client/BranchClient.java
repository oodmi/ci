package org.oodmi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Request;
import org.oodmi.model.Branch;
import org.oodmi.model.GithubSettings;
import org.oodmi.model.external.branch.BranchExternal;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * Client for operations with pull branches.
 */
public final class BranchClient {

    private GitHubHttpExecutor gitHubHttpExecutor;

    public BranchClient(GithubSettings properties) {
        this.gitHubHttpExecutor = new GitHubHttpExecutor(properties);
    }

    BranchClient() {
    }

    void setGitHubHttpExecutor(GitHubHttpExecutor gitHubHttpExecutor) {
        this.gitHubHttpExecutor = gitHubHttpExecutor;
    }

    /**
     * Retrieve list of branches
     *
     * @return list of branches
     */
    public CompletableFuture<Collection<Branch>> getBranches() {
        return gitHubHttpExecutor.newCall(new Request.Builder().get(), "/branches")
                .thenApply(response -> ObjectJsonMapper.getInstance().toObject(response, new TypeReference<Collection<BranchExternal>>() {
                }))
                .thenApply(list -> list.stream().map(BranchExternal::map).collect(Collectors.toList()));
    }
}
