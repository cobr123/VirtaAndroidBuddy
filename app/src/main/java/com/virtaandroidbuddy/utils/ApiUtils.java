package com.virtaandroidbuddy.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.virtaandroidbuddy.R;
import com.virtaandroidbuddy.data.api.VirtonomicaApi;
import com.virtaandroidbuddy.data.api.deserializer.CityJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.CompanyJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.CountryJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.KnowledgeJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.RegionJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.UnitClassJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.UnitListJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.UnitSummaryJsonDeserializer;
import com.virtaandroidbuddy.data.api.deserializer.UnitTypeJsonDeserializer;
import com.virtaandroidbuddy.data.api.interceptor.AddCookiesInterceptor;
import com.virtaandroidbuddy.data.api.interceptor.ResponseCodeInterceptor;
import com.virtaandroidbuddy.data.api.interceptor.ReceivedCookiesInterceptor;
import com.virtaandroidbuddy.data.api.model.CompanyJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;
import com.virtaandroidbuddy.data.database.model.City;
import com.virtaandroidbuddy.data.database.model.Country;
import com.virtaandroidbuddy.data.database.model.Knowledge;
import com.virtaandroidbuddy.data.database.model.Region;
import com.virtaandroidbuddy.data.database.model.UnitClass;
import com.virtaandroidbuddy.data.database.model.UnitType;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;


public class ApiUtils {
    public static final List<Class<?>> NETWORK_EXCEPTIONS = Arrays.asList(
            UnknownHostException.class,
            SocketTimeoutException.class,
            ConnectException.class
    );
    static private OkHttpClient client = null;
    static private Retrofit retrofit = null;
    static private VirtonomicaApi api = null;

    public static OkHttpClient getClient(Context appContext) {
        if (client == null) {
            client = new OkHttpClient.Builder()
                    .addInterceptor(new AddCookiesInterceptor(appContext))
                    .addInterceptor(new ReceivedCookiesInterceptor(appContext))
                    .addInterceptor(new ResponseCodeInterceptor())
                    //.addInterceptor(new LoggingInterceptor())
                    .build();
        }
        return client;
    }

    public static Retrofit getRetrofit(OkHttpClient client, String baseUrl) {
        if (retrofit == null) {
            final Gson gson = new GsonBuilder()
                    .registerTypeAdapter(CompanyJson.class, new CompanyJsonDeserializer())
                    .registerTypeAdapter(UnitListJson.class, new UnitListJsonDeserializer())
                    .registerTypeAdapter(UnitSummaryJson.class, new UnitSummaryJsonDeserializer())
                    .registerTypeAdapter(new TypeToken<List<Country>>() {
                    }.getType(), new CountryJsonDeserializer())
                    .registerTypeAdapter(new TypeToken<List<Region>>() {
                    }.getType(), new RegionJsonDeserializer())
                    .registerTypeAdapter(new TypeToken<List<City>>() {
                    }.getType(), new CityJsonDeserializer())
                    .registerTypeAdapter(new TypeToken<Knowledge>() {
                    }.getType(), new KnowledgeJsonDeserializer())
                    .registerTypeAdapter(new TypeToken<List<UnitClass>>() {
                    }.getType(), new UnitClassJsonDeserializer())
                    .registerTypeAdapter(new TypeToken<List<UnitType>>() {
                    }.getType(), new UnitTypeJsonDeserializer())
                    .create();
            retrofit = new Retrofit.Builder()
                    .baseUrl(baseUrl)
                    .client(client)
                    .addConverterFactory(GsonConverterFactory.create(gson))
                    .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    public static VirtonomicaApi getApiService(Context context) {
        if (api == null) {
            api = ApiUtils.getRetrofit(getClient(context), context.getString(R.string.base_url)).create(VirtonomicaApi.class);
        }
        return api;
    }

    private static SharedPreferences getSharedPreferences(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    static private final String REALM_KEY = "REALM";

    public static String getRealm(Context context) {
        return getSharedPreferences(context).getString(REALM_KEY, null);
    }

    public static void setRealm(Context context, String realm) {
        getSharedPreferences(context)
                .edit()
                .putString(REALM_KEY, realm)
                .apply();

    }

    static private final String PREF_COOKIES_KEY = "PREF_COOKIES";

    public static Set<String> getCookies(Context context) {
        return getSharedPreferences(context).getStringSet(PREF_COOKIES_KEY, new HashSet<>());
    }

    public static void setCookies(Context context, Set<String> cookies) {
        getSharedPreferences(context)
                .edit()
                .putStringSet(PREF_COOKIES_KEY, cookies)
                .apply();

    }
}