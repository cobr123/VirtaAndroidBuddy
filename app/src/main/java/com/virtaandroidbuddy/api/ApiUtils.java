package com.virtaandroidbuddy.api;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiUtils {
    static private OkHttpClient client = null;
    static private Retrofit retrofit = null;

    public static OkHttpClient getClient(Context appContext) {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(new AddCookiesInterceptor(appContext))
                    .addInterceptor(new ReceivedCookiesInterceptor(appContext))
                    .build();
        }
        return client;
    }

    public static Retrofit getRetrofit(OkHttpClient client, String baseUrl) {
        if (retrofit == null) {
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static void loginUser(OkHttpClient client, String baseUrl, String login, String password) throws IOException {
        RequestBody authRequestBody = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("userData[login]", login)
                .addFormDataPart("userData[password]", password)
                .addFormDataPart("userData[lang]", "ru")
                .build();
        Request authRequest = new Request.Builder()
                .url(baseUrl + "vera/main/user/login")
                .post(authRequestBody)
                .build();
        client.newCall(authRequest).execute();
    }

    public static void changeRealm(OkHttpClient client, String baseUrl, String realm) throws IOException {
        Request request = new Request.Builder()
                .url(baseUrl + realm + "/main/user/privat/headquarters")
                .build();
        client.newCall(request).execute();
    }

    public static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static private final String REALM_KEY = "REALM";

    public static String getRealm(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(REALM_KEY, null);
    }

    public static void setRealm(Context context, String realm) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putString(REALM_KEY, realm)
                .apply();

    }

    static private final String PREF_COOKIES_KEY = "PREF_COOKIES";

    public static Set<String> getCookies(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context).getStringSet(PREF_COOKIES_KEY, new HashSet<String>());
    }

    public static void setCookies(Context context, Set<String> cookies) {
        PreferenceManager.getDefaultSharedPreferences(context)
                .edit()
                .putStringSet(PREF_COOKIES_KEY, cookies)
                .apply();

    }
}