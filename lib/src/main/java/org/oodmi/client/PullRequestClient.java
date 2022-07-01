package org.oodmi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.oodmi.enums.State;
import org.oodmi.model.commit.Commit;
import org.oodmi.model.pull.PullRequest;
import org.oodmi.model.pull.PullState;
import org.oodmi.model.request.OpenPullRequest;
import org.oodmi.model.response.MergeResponse;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

import static org.oodmi.client.ConstantHolder.objectJsonMapper;

/**
 * Client for operations with pul requests.
 */
public class PullRequestClient {

    private final GitHubHttpExecutor gitHubHttpExecutor;

    public PullRequestClient(String auth, String name, String project) {
        this.gitHubHttpExecutor = new GitHubHttpExecutor(auth, name, project);
    }

    /**
     * Retrieve list of pull requests
     *
     * @param page   default 1
     * @param state  default opened
     * @param author need to use with branch
     * @param branch need to use with author
     * @return list of pull requests
     */
    public CompletableFuture<Collection<PullRequest>> getPulls(@Nullable Integer page,
                                                               @Nullable State state,
                                                               @Nullable String author,
                                                               @Nullable String branch) {
        StringBuilder builder = new StringBuilder();
        builder.append("/pulls");

        if (page != null || state != null || (author != null && branch != null)) {
            builder.append("?");
        }
        if (page != null) {
            builder.append("&page=");
            builder.append(page);
        }
        if (state != null) {
            builder.append("&state=");
            builder.append(state);
        }
        if (author != null && branch != null) {
            builder.append("&head=");
            builder.append(author + ":" + branch);
        }

        return gitHubHttpExecutor.newCall(new Request.Builder().get(), builder.toString())
                .thenApply(response -> objectJsonMapper.toObject(response, new TypeReference<>() {
                }));
    }

    /**
     * Open new pull request
     *
     * @param pullRequest information for new pull request
     * @return created pull request
     */
    public CompletableFuture<PullRequest> openPull(@NotNull OpenPullRequest pullRequest) {
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), objectJsonMapper.toJson(pullRequest));

        return gitHubHttpExecutor.newCall(new Request.Builder().post(body), "/pulls")
                .thenApply(response -> objectJsonMapper.toObject(response, new TypeReference<>() {
                }));
    }

    /**
     * Retrieve list of commits for specific pull request
     *
     * @param number pull request number
     * @return list of commits
     */
    public CompletableFuture<Collection<Commit>> getPullCommits(@NotNull Integer number) {
        return gitHubHttpExecutor.newCall(new Request.Builder().get(), String.format("/pulls/%s/commits", number))
                .thenApply(response -> objectJsonMapper.toObject(response, new TypeReference<>() {
                }));
    }

    /**
     * Merge specific pull request
     *
     * @param number pull request number
     * @return true - if merged, false - if not
     */
    public CompletableFuture<Boolean> mergePull(@NotNull Integer number) {
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), "");

        return gitHubHttpExecutor.newCall(new Request.Builder().put(body), String.format("/pulls/%s/merge", number))
                .thenApply(response -> objectJsonMapper.toObject(response, new TypeReference<MergeResponse>() {
                }).isMerged());
    }

    /**
     * Close specific pull request
     *
     * @param number pull request number
     * @return updated pull request
     */
    public CompletableFuture<PullRequest> closePull(@NotNull Integer number) {
        return managePull(number, new PullState("closed"));
    }

    /**
     * Reopen specific pull request
     *
     * @param number pull request number
     * @return updated pull request
     */
    public CompletableFuture<PullRequest> reopenPull(@NotNull Integer number) {
        return managePull(number, new PullState("open"));
    }

    private CompletableFuture<PullRequest> managePull(Integer number, PullState state) {
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), objectJsonMapper.toJson(state));

        return gitHubHttpExecutor.newCall(new Request.Builder().patch(body), String.format("/pulls/%s", number))
                .thenApply(response -> objectJsonMapper.toObject(response, new TypeReference<>() {
                }));
    }
}
