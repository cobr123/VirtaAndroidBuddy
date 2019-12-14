package com.virtaandroidbuddy.api.interceptor;

import android.util.Log;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class LoggingInterceptor implements Interceptor {
    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request request = chain.request();
        long t1 = System.nanoTime();
        Log.d("VirtonomicaApi", String.format("Sending request %s on %s%n%s",
                request.url(), chain.connection(), request.headers()));

        Response response = chain.proceed(request);

        long t2 = System.nanoTime();
        Log.d("VirtonomicaApi", String.format("Received response for %s in %.1fms%n%s%s",
                response.request().url(), (t2 - t1) / 1e6d, response.headers(), response.body().string()));

        return response;
    }
}
