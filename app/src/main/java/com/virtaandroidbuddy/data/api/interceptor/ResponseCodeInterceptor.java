package com.virtaandroidbuddy.data.api.interceptor;

import android.util.Log;

import com.virtaandroidbuddy.data.api.GameUpdateHappeningNowException;
import com.virtaandroidbuddy.data.api.SessionExpiredException;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class ResponseCodeInterceptor implements Interceptor {
    private static final String TAG = ResponseCodeInterceptor.class.getSimpleName();

    @Override
    public Response intercept(final Chain chain) throws IOException {
        final Request request = chain.request();
        final okhttp3.Response response = chain.proceed(request);

        if (response.code() == 423) {
            Log.d(TAG, response.toString());
            throw new GameUpdateHappeningNowException();
        } else if (response.code() == 500) {
            Log.d(TAG, response.toString());
            throw new SessionExpiredException();
        }

        return response;
    }
}
