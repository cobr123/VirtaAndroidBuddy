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
    @ColumnInfo(name = "city_name")
    private String mCityName;

    @ColumnInfo(name = "region_id")
    private String mRegionId;
    @ColumnInfo(name = "region_name")
    private String mRegionName;

    @ColumnInfo(name = "country_id")
    private String mCountryId;
    @ColumnInfo(name = "country_symbol")
    private String mCountrySymbol;
    @ColumnInfo(name = "country_name")
    private String mCountryName;

    @ColumnInfo(name = "company_id")
    private String mCompanyId;
    @ColumnInfo(name = "name")
    private String mName;
    @ColumnInfo(name = "unit_productivity")
    private double mUnitProductivity;

    @ColumnInfo(name = "unit_class_id")
    private String mUnitClassId;
    @ColumnInfo(name = "unit_class_name")
    private String mUnitClassName;

    @ColumnInfo(name = "unit_type_id")
    private String mUnitTypeId;
    @ColumnInfo(name = "unit_type_symbol")
    private String mUnitTypeSymbol;
    @ColumnInfo(name = "unit_type_name")
    private String mUnitTypeName;

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

    public String getCityName() {
        return mCityName;
    }

    public void setCityName(String cityName) {
        mCityName = cityName;
    }

    public String getRegionId() {
        return mRegionId;
    }

    public void setRegionId(String regionId) {
        mRegionId = regionId;
    }

    public String getRegionName() {
        return mRegionName;
    }

    public void setRegionName(String regionName) {
        mRegionName = regionName;
    }

    public String getCountryId() {
        return mCountryId;
    }

    public void setCountryId(String countryId) {
        mCountryId = countryId;
    }

    public String getCountrySymbol() {
        return mCountrySymbol;
    }

    public void setCountrySymbol(String countrySymbol) {
        mCountrySymbol = countrySymbol;
    }

    public String getCountryName() {
        return mCountryName;
    }

    public void setCountryName(String countryName) {
        mCountryName = countryName;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public double getUnitProductivity() {
        return mUnitProductivity;
    }

    public void setUnitProductivity(double unitProductivity) {
        mUnitProductivity = unitProductivity;
    }

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getUnitClassId() {
        return mUnitClassId;
    }

    public void setUnitClassId(String unitClassId) {
        mUnitClassId = unitClassId;
    }

    public String getUnitClassName() {
        return mUnitClassName;
    }

    public void setUnitClassName(String unitClassName) {
        mUnitClassName = unitClassName;
    }

    public String getUnitTypeId() {
        return mUnitTypeId;
    }

    public void setUnitTypeId(String unitTypeId) {
        mUnitTypeId = unitTypeId;
    }

    public String getUnitTypeSymbol() {
        return mUnitTypeSymbol;
    }

    public void setUnitTypeSymbol(String unitTypeSymbol) {
        mUnitTypeSymbol = unitTypeSymbol;
    }

    public String getUnitTypeName() {
        return mUnitTypeName;
    }

    public void setUnitTypeName(String unitTypeName) {
        mUnitTypeName = unitTypeName;
    }
}
