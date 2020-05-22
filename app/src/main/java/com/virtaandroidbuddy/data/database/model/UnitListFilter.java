package com.virtaandroidbuddy.data.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"realm", "company_id"})
public class UnitListFilter {
    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "company_id")
    private String mCompanyId;

    @ColumnInfo(name = "city_id")
    private String mCityId;

    @ColumnInfo(name = "region_id")
    private String mRegionId;

    @ColumnInfo(name = "country_id")
    private String mCountryId;

    public UnitListFilter() {
    }

    @Ignore
    public UnitListFilter(@NonNull String realm, @NonNull String companyId) {
        this.mRealm = realm;
        this.mCompanyId = companyId;

        this.mCountryId = "0";
        this.mRegionId = "0";
        this.mCityId = "0";
    }

    @NonNull
    public String getRealm() {
        return mRealm;
    }

    public void setRealm(@NonNull String realm) {
        mRealm = realm;
    }

    @NonNull
    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(@NonNull String companyId) {
        mCompanyId = companyId;
    }

    public String getCityId() {
        return mCityId;
    }

    public void setCityId(String cityId) {
        mCityId = cityId;
    }

    public String getRegionId() {
        return mRegionId;
    }

    public void setRegionId(String regionId) {
        mRegionId = regionId;
    }

    public String getCountryId() {
        return mCountryId;
    }

    public void setCountryId(String countryId) {
        mCountryId = countryId;
    }

    @Override
    public String toString() {
        return "UnitListFilter{" +
                "mRealm='" + mRealm + '\'' +
                ", mCompanyId='" + mCompanyId + '\'' +
                ", mCityId='" + mCityId + '\'' +
                ", mRegionId='" + mRegionId + '\'' +
                ", mCountryId='" + mCountryId + '\'' +
                '}';
    }
}
