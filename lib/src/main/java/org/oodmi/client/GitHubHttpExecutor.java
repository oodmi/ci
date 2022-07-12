package org.oodmi.client;

import com.fasterxml.jackson.core.type.TypeReference;
import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.oodmi.exceptions.GithubException;
import org.oodmi.interseptor.RequestHeadersNetworkInterceptor;
import org.oodmi.interseptor.RetryInterceptor;
import org.oodmi.model.GithubSettings;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

public final class GitHubHttpExecutor {

    private final OkHttpClient client;
    private final String basePath;
    private String host;
    private String port;

    public GitHubHttpExecutor(GithubSettings properties) {
        this(properties, null, null);
    }

    GitHubHttpExecutor(GithubSettings properties, String host, Integer port) {
        if (host != null && port != null) {
            this.basePath = "http://" + host + ":" + port + "/" + properties.name() + "/" + properties.project();
        } else {
            this.basePath = "https://api.github.com/repos/" + properties.name() + "/" + properties.project();
        }
        this.client = new OkHttpClient()
                .newBuilder()
                .readTimeout(20, TimeUnit.SECONDS)
                .connectTimeout(20, TimeUnit.SECONDS)
                .addInterceptor(new RequestHeadersNetworkInterceptor(properties.token()))
                .addInterceptor(new RetryInterceptor(properties.retryLimit()))
                .retryOnConnectionFailure(true)
                .build();
    }

    CompletableFuture<Response> newCall(@NotNull Request.Builder requestBuilder, @NotNull String relativePath) {
        requestBuilder.url(basePath + relativePath);
        Request request = requestBuilder.build();
        CompletableFuture<Response> result = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                result.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                if (response.isSuccessful()) {
                    result.complete(response);
                } else {
                    HashMap<String, String> res = ObjectJsonMapper.getInstance().toObject(response, new TypeReference<>() {
                    });

                    result.completeExceptionally(new GithubException(
                            response.code(),
                            res.get("message"),
                            res.get("documentation_url"))
                    );
                }
            }
        });
        return result;
    }
}
