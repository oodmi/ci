package org.oodmi.interseptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class RequestHeadersNetworkInterceptor implements Interceptor {

    private final Map<String, String> headers;

    public RequestHeadersNetworkInterceptor(@NotNull String token) {
        this.headers = new HashMap<>() {
            {
                put("Authorization", "token " + token);
                put("Accept", "application/vnd.github.v3+json");
            }
        };
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            if (header.getKey() == null || header.getKey().trim().isEmpty()) {
                continue;
            }
            if (header.getValue() == null || header.getValue().trim().isEmpty()) {
                builder.removeHeader(header.getKey());
            } else {
                builder.header(header.getKey(), header.getValue());
            }
        }
        return chain.proceed(builder.build());
    }

}