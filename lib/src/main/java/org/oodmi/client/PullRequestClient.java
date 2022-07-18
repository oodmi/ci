package org.oodmi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.MediaType;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.oodmi.model.*;
import org.oodmi.model.external.GraphqlRequestExternal;
import org.oodmi.model.external.commit.CommitExternal;
import org.oodmi.model.external.graphql.GraphqlExternal;
import org.oodmi.model.external.pull.PullRequestExternal;
import org.oodmi.model.external.pull.PullStateExternal;
import org.oodmi.model.external.request.OpenPullRequestExternal;
import org.oodmi.model.external.response.MergeResponseExternal;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;


/**
 * Client for operations with pul requests.
 */
public final class PullRequestClient {

    static final String JSON_HEADER = "application/json; charset=utf-8";
    private final PullRequestClient _this = this;
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
                                                                      @Nullable Integer perPage,
                                                                      @Nullable PullRequestFilter filter) {
        page = (page == null || page < 0) ? 1 : page;
        perPage = (perPage == null || perPage < 0) ? 5 : perPage;

        CompletableFuture<PullRequestPage> future = this.getPullRequestPage(perPage, filter);
        while (page > 1) {
            future = future.thenCompose(PullRequestPage::nextPage);
            page--;
        }

        return future.thenApply(PullRequestPage::get);
    }

    /**
     * Retrieves page of pull requests
     *
     * @param perPage default 5
     * @param filter  filter for getting pull request
     * @return page
     */
    public CompletableFuture<PullRequestPage> getPullRequestPage(@Nullable Integer perPage,
                                                                 @Nullable PullRequestFilter filter) {
        return getPullRequestPage(perPage, null, filter);
    }

    CompletableFuture<PullRequestPage> getPullRequestPage(@Nullable Integer perPage,
                                                          @Nullable String cursor,
                                                          @Nullable PullRequestFilter filter) {
        Integer checkedPerPage = (perPage == null || perPage < 0) ? 5 : perPage;
        String searchQuery = buildSearchQuery(filter);

        String search = readStringFromFile(getClass(), "/graphql/search.txt");

        search = search.replace("{query}", searchQuery)
                .replace("{perPage}", checkedPerPage.toString());

        if (cursor == null) {
            search = search.replace("{after}", "");
        } else {
            search = search.replace("{after}", "after: \"" + cursor + "\"\n");
        }

        String request = ObjectJsonMapper.getInstance().toJson(new GraphqlRequestExternal()
                .setQuery(search));

        return gitHubHttpExecutor.newGraphqlCall(request)
                .thenApply(response -> getPullRequestPage(filter, checkedPerPage, response));
    }

    private PullRequestPageImpl getPullRequestPage(@Nullable PullRequestFilter filter,
                                                   Integer checkedPerPage,
                                                   Response response) {
        GraphqlExternal graphqlExternal = ObjectJsonMapper.getInstance().toObject(response, new TypeReference<>() {
        });
        String endCursor = graphqlExternal.getData().getSearch().getPageInfo().getEndCursor();
        Boolean hasNextPage = graphqlExternal.getData().getSearch().getPageInfo().getHasNextPage();
        return new PullRequestPageImpl(checkedPerPage, endCursor, hasNextPage, graphqlExternal.map(), filter, _this);
    }

    @NotNull
    private String buildSearchQuery(@Nullable PullRequestFilter filter) {
        StringBuilder builder = new StringBuilder();
        builder.append("is:pr")
                .append(" repo:{repo}");

        if (filter != null) {
            if (filter.getState() != null) {
                switch (filter.getState()) {
                    case closed -> builder.append(" is:closed");
                    case opened -> builder.append(" is:open");
                }
            } else {
                builder.append(" is:open");
            }
            if (filter.getInto() != null) {
                builder.append(" base:");
                builder.append(filter.getInto());
            }
            if (filter.getAuthor() != null) {
                builder.append(" author:");
                builder.append(filter.getAuthor());
            }
            if (filter.getFrom() != null) {
                builder.append(" head:");
                builder.append(filter.getFrom());
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

        RequestBody body = RequestBody.create(MediaType.get(JSON_HEADER), ObjectJsonMapper.getInstance().toJson(openPullRequest));

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
        RequestBody body = RequestBody.create(MediaType.get(JSON_HEADER), "");

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
        RequestBody body = RequestBody.create(MediaType.get(JSON_HEADER), ObjectJsonMapper.getInstance().toJson(state));

        return gitHubHttpExecutor.newCall(new Request.Builder().patch(body), String.format("/pulls/%s", number))
                .thenApply(response -> ObjectJsonMapper.getInstance().toObject(response, new TypeReference<PullRequestExternal>() {
                }))
                .thenApply(PullRequestExternal::map);
    }

    public String readStringFromFile(Class<?> clazz, String path) {
        try {
            return IOUtils.toString(Objects.requireNonNull(clazz.getResourceAsStream(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("Error reading file {0}", path));
        }
    }
}
