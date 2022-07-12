package org.oodmi.client;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.apache.commons.io.IOUtils;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.oodmi.exceptions.GithubException;
import org.oodmi.model.Branch;
import org.oodmi.model.GithubSettings;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.MessageFormat;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ExecutionException;

import static org.junit.jupiter.api.Assertions.*;

public class BranchClientTest {
    static BranchClient branchClient;
    static GithubSettings properties;
    final MockWebServer server = new MockWebServer();

    @BeforeAll
    public static void beforeAll() {
        properties = new GithubSettings("token", "name", "project", 5);
        branchClient = new BranchClient();
    }

    @Test
    public void getBranches_Test() throws Exception {
        String branchListResponse = readStringFromFile(getClass(), "/branch_list.json");
        Collection<Branch> expected =
                List.of(new Branch().setName("feature/1").setSha("8d9813a5869c52e11dc358d52484e4941863bebc"),
                        new Branch().setName("feature/2").setSha("39aa87811288658a1592022336856506a663c8fa"),
                        new Branch().setName("main").setSha("3d359d56a2e068cf16ff9f6bde6aa8a7bab4c5a3"));

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/branches".equals(request.getPath())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody(branchListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        branchClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        Collection<Branch> branches = branchClient.getBranches().get();

        assertEquals(3, branches.size());
        assertEquals(expected, branches);

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/branches"), recordedRequest.getRequestUrl());
        server.close();
    }

    @Test
    public void getBranchesBadCredentials_Test() throws Exception {
        String branchListResponse = readStringFromFile(getClass(), "/bad_credintials.json");

        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project/branches".equals(request.getPath())) {
                    return new MockResponse()
                            .setResponseCode(401)
                            .setBody(branchListResponse)
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        branchClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties,
                server.getHostName(),
                server.getPort()));

        ExecutionException executionException = assertThrows(ExecutionException.class,
                () -> branchClient.getBranches().get());
        assertInstanceOf(GithubException.class, executionException.getCause());

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(server.url("/name/project/branches"), recordedRequest.getRequestUrl());
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