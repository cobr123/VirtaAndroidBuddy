package com.virtaandroidbuddy.api;

import com.virtaandroidbuddy.api.model.Company;
import com.virtaandroidbuddy.api.model.Unit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface VirtonomicaApi {
    @GET("api/{realm}/main/my/company")
    Call<Company> getCompanyInfo(@Path("realm") String realm);

    @GET("api/{realm}/my/company/units")
    Call<List<Unit>> getUnitList(@Path("realm") String realm, @Query("id") String company_id);
}
