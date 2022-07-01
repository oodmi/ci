package org.oodmi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.Request;
import org.oodmi.model.branch.Branch;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static org.oodmi.client.ConstantHolder.objectJsonMapper;

/**
 * Client for operations with pul branches.
 */
public class BranchClient {

    private final GitHubHttpExecutor gitHubHttpExecutor;

    public BranchClient(String auth, String name, String project) {
        this.gitHubHttpExecutor = new GitHubHttpExecutor(auth, name, project);
    }

    /**
     * Retrieve list of branches
     *
     * @return list of branches
     */
    public CompletableFuture<Collection<Branch>> getBranches() {
        return gitHubHttpExecutor.newCall(new Request.Builder().get(), "/branches")
                .thenApply(response -> objectJsonMapper.toObject(response, new TypeReference<>() {
                }));
    }
}
