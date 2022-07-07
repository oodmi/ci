package org.oodmi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.oodmi.model.*;
import org.oodmi.model.external.commit.CommitExternal;
import org.oodmi.model.external.pull.PullRequestExternal;
import org.oodmi.model.external.pull.PullStateExternal;
import org.oodmi.model.external.request.OpenPullRequestExternal;
import org.oodmi.model.external.response.MergeResponseExternal;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


/**
 * Client for operations with pul requests.
 */
public final class PullRequestClient {

    private GitHubHttpExecutor gitHubHttpExecutor;

    public PullRequestClient(GithubSettings properties) {
        this.gitHubHttpExecutor = new GitHubHttpExecutor(properties);
    }

    PullRequestClient() {
    }

    void setGitHubHttpExecutor(GitHubHttpExecutor gitHubHttpExecutor) {
        this.gitHubHttpExecutor = gitHubHttpExecutor;
    }

    /**
     * Retrieves list of pull requests
     *
     * @param page   default 1
     * @param filter filter for getting pull request
     * @return list of pull requests
     */
    public CompletableFuture<Collection<PullRequest>> getPullRequests(@Nullable Integer page,
                                                                      @Nullable PullRequestFilter filter) {
        String url = getUrl(page, filter);

        return gitHubHttpExecutor.newCall(new Request.Builder().get(), url)
                .thenApply(response -> ObjectJsonMapper.getInstance().toObject(response, new TypeReference<Collection<PullRequestExternal>>() {
                }))
                .thenApply(list -> list.stream().map(PullRequestExternal::map).collect(Collectors.toList()));
    }

    @NotNull
    private String getUrl(@Nullable Integer page, @Nullable PullRequestFilter filter) {
        StringBuilder builder = new StringBuilder();
        page = page == null ? 1 : page;
        builder.append("/pulls?page=").append(page);

        if (filter != null) {
            if (filter.getState() != null) {
                builder.append("&state=");
                builder.append(filter.getState());
            }
            if (filter.getInto() != null) {
                builder.append("&base=");
                builder.append(filter.getInto());
            }
            if (filter.getAuthor() != null && filter.getFrom() != null) {
                builder.append("&head=");
                builder.append(filter.getAuthor()).append(":").append(filter.getFrom());
            }
        }
        return builder.toString();
    }

    /**
     * Open new pull request
     *
     * @param request information for new pull request
     * @return created pull request
     */
    public CompletableFuture<PullRequest> openPullRequest(@NotNull OpenPullRequest request) {
        OpenPullRequestExternal openPullRequest = new OpenPullRequestExternal()
                .setBase(request.getInto())
                .setHead(request.getFrom())
                .setBody(request.getBody())
                .setTitle(request.getTitle());

        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), ObjectJsonMapper.getInstance().toJson(openPullRequest));

        return gitHubHttpExecutor.newCall(new Request.Builder().post(body), "/pulls")
                .thenApply(response -> ObjectJsonMapper.getInstance().toObject(response, new TypeReference<PullRequestExternal>() {
                }))
                .thenApply(PullRequestExternal::map);
    }

    /**
     * Retrieve list of commits for specific pull request
     *
     * @param number pull request number
     * @return list of commits
     */
    public CompletableFuture<Collection<Commit>> getPullRequestCommits(@NotNull Integer number) {
        return gitHubHttpExecutor.newCall(new Request.Builder().get(), String.format("/pulls/%s/commits", number))
                .thenApply(response -> ObjectJsonMapper.getInstance().toObject(response, new TypeReference<Collection<CommitExternal>>() {
                }))
                .thenApply(list -> list.stream().map(CommitExternal::map).collect(Collectors.toList()));
    }

    /**
     * Merge specific pull request
     *
     * @param number pull request number
     * @return true - if merged, false - if not
     */
    public CompletableFuture<Boolean> mergePullRequest(@NotNull Integer number) {
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), "");

        return gitHubHttpExecutor.newCall(new Request.Builder().put(body), String.format("/pulls/%s/merge", number))
                .thenApply(response -> ObjectJsonMapper.getInstance().toObject(response, new TypeReference<MergeResponseExternal>() {
                }).isMerged());
    }

    /**
     * Close specific pull request
     *
     * @param number pull request number
     * @return updated pull request
     */
    public CompletableFuture<PullRequest> closePullRequest(@NotNull Integer number) {
        return managePullRequest(number, new PullStateExternal("closed"));
    }

    /**
     * Reopen specific pull request
     *
     * @param number pull request number
     * @return updated pull request
     */
    public CompletableFuture<PullRequest> reopenPullRequest(@NotNull Integer number) {
        return managePullRequest(number, new PullStateExternal("open"));
    }

    private CompletableFuture<PullRequest> managePullRequest(Integer number, PullStateExternal state) {
        RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), ObjectJsonMapper.getInstance().toJson(state));

        return gitHubHttpExecutor.newCall(new Request.Builder().patch(body), String.format("/pulls/%s", number))
                .thenApply(response -> ObjectJsonMapper.getInstance().toObject(response, new TypeReference<PullRequestExternal>() {
                }))
                .thenApply(PullRequestExternal::map);
    }
}
