package com.virtaandroidbuddy.data.api;

import com.virtaandroidbuddy.data.api.model.CompanyJson;
import com.virtaandroidbuddy.data.api.model.UnitListJson;
import com.virtaandroidbuddy.data.api.model.UnitSummaryJson;
import com.virtaandroidbuddy.data.database.model.City;
import com.virtaandroidbuddy.data.database.model.Country;
import com.virtaandroidbuddy.data.database.model.Knowledge;
import com.virtaandroidbuddy.data.database.model.Region;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VirtonomicaApi {
    @FormUrlEncoded
    @POST("{realm}/main/user/login")
    Completable login(@Path("realm") String realm,
                      @Field("userData[login]") String login,
                      @Field("userData[password]") String password,
                      @Field("userData[lang]") String lang);

    @GET("api/{realm}/main/my/company")
    Single<CompanyJson> getCompanyInfo(@Path("realm") String realm);

    @GET("api/{realm}/my/company/units")
    Single<UnitListJson> getUnitList(@Path("realm") String realm,
                                     @Query("id") String company_id,
                                     @Query(value = "geo", encoded = true) String geo);

    @GET("api/{realm}/my/unit/summary")
    Single<UnitSummaryJson> getUnitSummary(@Path("realm") String realm, @Query("id") String unit_id);

    @GET("api/{realm}/main/user/competences/browse")
    Single<Knowledge> getKnowledge(@Path("realm") String realm);

    @GET("api/{realm}/main/geo/country/browse")
    Single<List<Country>> getCountryList(@Path("realm") String realm);

    @GET("api/{realm}/main/geo/region/browse")
    Single<List<Region>> getRegionList(@Path("realm") String realm);

    @GET("api/{realm}/main/geo/city/browse")
    Single<List<City>> getCityList(@Path("realm") String realm);
}
