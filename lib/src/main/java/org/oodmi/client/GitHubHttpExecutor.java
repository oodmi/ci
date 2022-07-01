package org.oodmi.client;

import okhttp3.*;
import org.jetbrains.annotations.NotNull;
import org.oodmi.interseptor.RequestHeadersNetworkInterceptor;

import java.io.IOException;
import java.util.concurrent.CompletableFuture;


final class GitHubHttpExecutor {

    private final OkHttpClient client;
    private final String basePath;

    public GitHubHttpExecutor(@NotNull String token,
                              @NotNull String name,
                              @NotNull String project) {
        this.basePath = "https://api.github.com/repos/" + name + "/" + project;
        this.client = new OkHttpClient()
                .newBuilder()
                .addInterceptor(new RequestHeadersNetworkInterceptor(token))
//                .addInterceptor(new ErrorResponseInterceptor())
                .build();
    }

    public CompletableFuture<Response> newCall(@NotNull Request.Builder requestBuilder, @NotNull String path) {
        requestBuilder.url(basePath + path);
        Request request = requestBuilder.build();
        CompletableFuture<Response> result = new CompletableFuture<>();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(@NotNull Call call, @NotNull IOException e) {
                result.completeExceptionally(e);
            }

            @Override
            public void onResponse(@NotNull Call call, @NotNull Response response) {
                result.complete(response);
            }
        });
        return result;
    }

    public CompletableFuture<Response> newCall(@NotNull Request.Builder requestBuilder) {
        return newCall(requestBuilder, "");
    }
}
