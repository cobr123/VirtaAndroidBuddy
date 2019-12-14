package com.virtaandroidbuddy.api.interceptor;

import android.content.Context;
import android.util.Log;

import com.virtaandroidbuddy.api.ApiUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
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
            final Map<String, String> cookieMap = new HashMap<>();
            for (String cookieLine : cookies) {
                for (String keyValue : cookieLine.split(";")) {
                    final String[] arr = keyValue.split("=");
                    if (arr.length == 2) {
                        cookieMap.put(arr[0].trim(), arr[1].trim());
                    }
                }
            }
            final Set<String> cookieSet = new HashSet<>();
            for (String key : cookieMap.keySet()) {
                final String keyValue = key + "=" + cookieMap.get(key);
                //Log.d("VirtonomicaApi.SCookie", keyValue);
                cookieSet.add(keyValue);
            }
            ApiUtils.setCookies(context, cookieSet);
        }

        return originalResponse;
    }
}