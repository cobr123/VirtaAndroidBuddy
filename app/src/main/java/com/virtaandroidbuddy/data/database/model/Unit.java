package com.virtaandroidbuddy.data.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"realm", "id"})
public class Unit {

    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "city_id")
    private String mCityId;

    @ColumnInfo(name = "region_id")
    private String mRegionId;

    @ColumnInfo(name = "country_id")
    private String mCountryId;

    @ColumnInfo(name = "company_id")
    private String mCompanyId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "unit_class_name")
    private String mUnitClassName;

    @NonNull
    public String getRealm() {
        return mRealm;
    }

    public void setRealm(@NonNull String realm) {
        mRealm = realm;
    }

    @NonNull
    public String getId() {
        return mId;
    }

    public void setId(@NonNull String id) {
        mId = id;
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getUnitClassName() {
        return mUnitClassName;
    }

    public void setUnitClassName(String unitClassName) {
        mUnitClassName = unitClassName;
    }
}
