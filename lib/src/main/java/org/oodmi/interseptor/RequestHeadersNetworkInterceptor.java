package org.oodmi.interseptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.Map;

public class RequestHeadersNetworkInterceptor implements Interceptor {

    private final Map<String, String> headers;

    public RequestHeadersNetworkInterceptor(@NotNull String token) {
        this.headers = Map.of(
                "Authorization", "token " + token,
                "Accept", "application/vnd.github.v3+json"
        );
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            builder.header(header.getKey(), header.getValue());
        }
        return chain.proceed(builder.build());
    }

}