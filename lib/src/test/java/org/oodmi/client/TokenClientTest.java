package org.oodmi.client;

import okhttp3.mockwebserver.Dispatcher;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.oodmi.model.GithubSettings;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenClientTest {
    static TokenClient tokenClient;
    static GithubSettings properties;
    final MockWebServer server = new MockWebServer();

    @BeforeAll
    public static void beforeAll() {
        properties = new GithubSettings("token", "name", "project", 1);
        tokenClient = new TokenClient();
    }

    @Test
    public void check_Test() throws Exception {
        Dispatcher dispatcher = new Dispatcher() {
            @NotNull
            @Override
            public MockResponse dispatch(RecordedRequest request) {
                if ("/name/project".equals(request.getPath())) {
                    return new MockResponse()
                            .setResponseCode(200)
                            .setBody("{}")
                            .addHeader("Content-Type", "application/json; charset=utf-8");
                }
                return new MockResponse().setResponseCode(404);
            }
        };

        server.setDispatcher(dispatcher);

        server.start();

        tokenClient.setGitHubHttpExecutor(new GitHubHttpExecutor(properties, server.getHostName(), server.getPort()));

        assertEquals(true, tokenClient.check().get());

        RecordedRequest recordedRequest = server.takeRequest();
        assertEquals("GET", recordedRequest.getMethod());
        assertEquals(server.url("/name/project"), recordedRequest.getRequestUrl());
        server.close();
    }
}