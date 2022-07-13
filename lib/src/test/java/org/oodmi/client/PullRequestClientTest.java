package org.oodmi.client;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.oodmi.model.Commit;
import org.oodmi.model.GithubSettings;
import org.oodmi.model.OpenPullRequest;
import org.oodmi.model.PullRequest;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class PullRequestClientTest {
    static PullRequestClient pullRequestClient;
    static GithubSettings properties;
    final MockWebServer server = new MockWebServer();

    @BeforeAll
    public static void beforeAll() {
        properties = new GithubSettings("token", "name", "project", 1);
        pullRequestClient = new PullRequestClient();
    }

    @Test
    public void getPullRequestList_Test() throws Exception {
        String pullRequestListResponse = readStringFromFile(getClass(), "/get_pull_request_list.json");
        Collection<PullRequest> expected =
                List.of(
                        new PullRequest()
                                .setName(null)
                                .setLogin("PakhomovAlexander")
                                .setEmail(null)
                                .setState("open")
                                .setTitle("Update README.md")
                                .setCreatedAt(LocalDateTime.of(2022, 7, 6, 14, 30, 7))
                                .setUpdatedAt(LocalDateTime.of(2022, 7, 7, 17, 2, 54))
                                .setInto("main")
                                .setFrom("pr-1")
                                .setNumber(15L)
                                .setSummary(null),
                        new PullRequest()
                                .setName(null)
                                .setLogin("oodmi")
                                .setEmail(null)
                                .setState("open")
                                .setTitle("123")
                                .setCreatedAt(LocalDateTime.of(2022, 7, 6, 9, 13, 46))
                                .setUpdatedAt(LocalDateTime.of(2022, 7, 6, 9, 13, 46))
                                .setInto("feature/4")
                                .setFrom("feature/6")
                                .setNumber(14L)
                                .setSummary(null)
                );

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/pulls?page=1".equals(request.getPath())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(pullRequestListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse()
                        .setResponseCode(404)
                        .setBody("{}");
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        pullRequestClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        Collection<PullRequest> pullRequests = pullRequestClient.getPullRequests(null, null).get();

        assertEquals(2, pullRequests.size());
        assertEquals(expected, pullRequests);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/pulls?page=1"), recordedRequest.getRequestUrl());
        server.close();
    }

    @Test
    public void getPullRequestCommitsByNumber_Test() throws Exception {
        String pullRequestListResponse = readStringFromFile(getClass(), "/get_pull_request_commits_by_number.json");
        Collection<Commit> expected =
                List.of(
                        new Commit()
                                .setDate(LocalDateTime.of(2022, 07, 06, 14, 29, 33))
                                .setMessage("Update README.md")
                                .setSha("54ad1f28e26611c5b65f27b21606ae0cb02bd3ef")
                                .setAuthor("Alexandr")
                                .setEmail("Sasha21031997@icloud.com")
                );

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/pulls/15/commits".equals(request.getPath())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(pullRequestListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse()
                        .setResponseCode(404)
                        .setBody("{}");
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        pullRequestClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        Collection<Commit> pullRequests = pullRequestClient.getPullRequestCommits(15).get();

        assertEquals(1, pullRequests.size());
        assertEquals(expected, pullRequests);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/pulls/15/commits"), recordedRequest.getRequestUrl());
        server.close();
    }

    @Test
    public void closeRequest_Test() throws Exception {
        String pullRequestListResponse = readStringFromFile(getClass(), "/close_pull_request.json");
        PullRequest expected =
                new PullRequest()
                        .setName(null)
                        .setLogin("PakhomovAlexander")
                        .setEmail(null)
                        .setState("closed")
                        .setTitle("Update README.md")
                        .setCreatedAt(LocalDateTime.of(2022, 7, 6, 14, 30, 7))
                        .setUpdatedAt(LocalDateTime.of(2022, 7, 9, 23, 23, 7))
                        .setInto("main")
                        .setFrom("pr-1")
                        .setNumber(15L)
                        .setSummary(null);

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/pulls/15".equals(request.getPath()) && "PATCH".equals(request.getMethod())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(pullRequestListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse()
                        .setResponseCode(404)
                        .setBody("{}");
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        pullRequestClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        PullRequest pullRequest = pullRequestClient.closePullRequest(15).get();

        assertEquals(expected, pullRequest);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("PATCH", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/pulls/15"), recordedRequest.getRequestUrl());
        server.close();
    }

    @Test
    public void reopenRequest_Test() throws Exception {
        String pullRequestListResponse = readStringFromFile(getClass(), "/reopen_pull_request.json");
        PullRequest expected =
                new PullRequest()
                        .setName(null)
                        .setLogin("PakhomovAlexander")
                        .setEmail(null)
                        .setState("open")
                        .setTitle("Update README.md")
                        .setCreatedAt(LocalDateTime.of(2022, 7, 6, 14, 30, 7))
                        .setUpdatedAt(LocalDateTime.of(2022, 7, 9, 23, 23, 27))
                        .setInto("main")
                        .setFrom("pr-1")
                        .setNumber(15L)
                        .setSummary(null);

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/pulls/15".equals(request.getPath()) && "PATCH".equals(request.getMethod())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(pullRequestListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse()
                        .setResponseCode(404)
                        .setBody("{}");
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        pullRequestClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        PullRequest pullRequest = pullRequestClient.closePullRequest(15).get();

        assertEquals(expected, pullRequest);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("PATCH", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/pulls/15"), recordedRequest.getRequestUrl());
        server.close();
    }

    @Test
    public void mergeRequest_Test() throws Exception {
        String pullRequestListResponse = readStringFromFile(getClass(), "/merge_pull_request.json");

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/pulls/15/merge".equals(request.getPath()) && "PUT".equals(request.getMethod())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(pullRequestListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse()
                        .setResponseCode(404)
                        .setBody("{}");
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        pullRequestClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        Boolean actual = pullRequestClient.mergePullRequest(15).get();

        assertEquals(true, actual);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("PUT", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/pulls/15/merge"), recordedRequest.getRequestUrl());
        server.close();
    }

    @Test
    public void openRequest_Test() throws Exception {
        String pullRequestListResponse = readStringFromFile(getClass(), "/open_pull_request_response.json");

        PullRequest expected = new PullRequest()
                .setName(null)
                .setLogin("oodmi")
                .setEmail(null)
                .setState("open")
                .setTitle("123")
                .setCreatedAt(LocalDateTime.of(2022, 7, 6, 9, 13, 46))
                .setUpdatedAt(LocalDateTime.of(2022, 7, 6, 9, 13, 46))
                .setInto("feature/4")
                .setFrom("feature/6")
                .setNumber(14L)
                .setSummary(null);

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/pulls".equals(request.getPath())
                        && "POST".equals(request.getMethod())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(pullRequestListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse()
                        .setResponseCode(404)
                        .setBody("{}");
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        pullRequestClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        PullRequest pullRequest = pullRequestClient.openPullRequest(new OpenPullRequest()
                .setInto("feature/4")
                .setFrom("feature/6")
                .setBody(null)
                .setTitle("123")).get();

        assertEquals(expected, pullRequest);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("POST", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/pulls"), recordedRequest.getRequestUrl());
        server.close();
    }

    public String readStringFromFile(Class<?> clazz, String path) {
        try {
            return IOUtils.toString(Objects.requireNonNull(clazz.getResourceAsStream(path)), StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new RuntimeException(MessageFormat.format("Error reading file {0}", path));
        }
    }
}