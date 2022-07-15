package org.oodmi.client;

import org.oodmi.model.PullRequest;

import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public interface PullRequestPage {

    Collection<PullRequest> get();

    CompletableFuture<PullRequestPage> nextPage();
}
