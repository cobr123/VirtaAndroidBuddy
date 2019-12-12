package com.virtaandroidbuddy.api;

import android.content.Context;
import android.util.Log;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {
    private final Context context;

    public ReceivedCookiesInterceptor(final Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Response originalResponse = chain.proceed(chain.request());

        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            final Set<String> cookies = new HashSet<>(originalResponse.headers("Set-Cookie"));

            Log.d("VirtonomicaApi.SCookie", cookies.toString());
            ApiUtils.setCookies(context, cookies);
        }

        return originalResponse;
    }
}