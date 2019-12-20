package com.virtaandroidbuddy.data.api.interceptor;

import android.content.Context;
import android.util.Log;

import com.virtaandroidbuddy.utils.ApiUtils;

import org.jsoup.internal.StringUtil;

import java.io.IOException;
import java.util.Set;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * This interceptor put all the Cookies in Preferences in the Request.
 * Your implementation on how to get the Preferences may ary, but this will work 99% of the time.
 */
public class AddCookiesInterceptor implements Interceptor {
    private final Context context;

    public AddCookiesInterceptor(final Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(final Interceptor.Chain chain) throws IOException {
        Log.d("VirtonomicaApi", chain.request().url().toString());
        final Request.Builder builder = chain.request().newBuilder();

        final Set<String> preferences = ApiUtils.getCookies(context);

        Log.d("VirtonomicaApi.ACookie", preferences.toString());
        if (!preferences.isEmpty()) {
            builder.addHeader("Cookie", StringUtil.join(preferences, "; "));
        }
//        for (String cookie : preferences) {
//            Log.d("VirtonomicaApi.ACookie", cookie);
//            builder.addHeader("Cookie", cookie);
//        }

        return chain.proceed(builder.build());
    }
}