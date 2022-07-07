package org.oodmi.interseptor;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;

public class RetryInterceptor implements Interceptor {

    private final Integer retryLimit;

    public RetryInterceptor(Integer retryLimit) {
        this.retryLimit = retryLimit;
    }

    @NotNull
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Response response = chain.proceed(request);
        var tryCount = 0;

        while (!response.isSuccessful()
                && response.code() != 401
                && response.code() != 404
                && tryCount < retryLimit) {
            tryCount++;
            response.close();
            response = chain.proceed(request);
        }

        return response;
    }
}