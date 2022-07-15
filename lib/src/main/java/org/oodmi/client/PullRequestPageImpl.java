package org.oodmi.client;

import org.oodmi.model.PullRequest;
import org.oodmi.model.PullRequestFilter;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public final class PullRequestPageImpl implements PullRequestPage {

    private final Integer perPage;
    private final String cursor;
    private final Collection<PullRequest> pullRequests;
    private final PullRequestFilter filter;
    private final PullRequestClient client;

    PullRequestPageImpl(Integer perPage,
                        String cursor,
                        Collection<PullRequest> pullRequests,
                        PullRequestFilter filter,
                        PullRequestClient client) {
        this.perPage = perPage;
        this.cursor = cursor;
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
        return client.getPullRequestPage(perPage, cursor, filter);
    }
}
