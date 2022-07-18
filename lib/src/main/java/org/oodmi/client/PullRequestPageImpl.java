package org.oodmi.client;

import org.oodmi.exceptions.GithubException;
import org.oodmi.model.PullRequest;
import org.oodmi.model.PullRequestFilter;

import java.util.Collection;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public final class PullRequestPageImpl implements PullRequestPage {

    private final Integer perPage;
    private final String cursor;
    private final Boolean hasNextPage;
    private final Collection<PullRequest> pullRequests;
    private final PullRequestFilter filter;
    private final PullRequestClient client;

    PullRequestPageImpl(Integer perPage,
                        String cursor,
                        Boolean hasNextPage,
                        Collection<PullRequest> pullRequests,
                        PullRequestFilter filter,
                        PullRequestClient client) {
        this.perPage = perPage;
        this.cursor = cursor;
        this.hasNextPage = hasNextPage;
        this.pullRequests = pullRequests;
        this.filter = filter;
        this.client = client;
    }

    @Override
    public Collection<PullRequest> get() {
        return pullRequests;
    }

    @Override
    public CompletableFuture<PullRequestPage> nextPage() {
        if (!hasNextPage) {
            CompletableFuture<PullRequestPage> pullRequestPageCompletableFuture = new CompletableFuture<>();
            pullRequestPageCompletableFuture.completeExceptionally(new GithubException(null, "Next page is not available", null, List.of()));
            return pullRequestPageCompletableFuture;
        }
        return client.getPullRequestPage(perPage, cursor, filter);
    }
}
