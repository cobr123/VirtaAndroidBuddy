package com.virtaandroidbuddy.data.api.model;


import java.io.Serializable;

public class UnitListDataJson implements Serializable {
    private String mId;
    private String mName;
    private double mUnitProductivity;

    private String mUnitTypeId;
    private String mUnitTypeSymbol;
    private String mUnitTypeName;

    private String mUnitClassId;
    private String mUnitClassName;
    private String mUnitClassKind;

    private String mCountryId;
    private String mCountrySymbol;
    private String mCountryName;

    private String mRegionId;
    private String mRegionName;

    private String mCityId;
    private String mCityName;

    private boolean mWorkersInHoliday;

    public String getId() {
        return mId;
    }

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
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

    public String getUnitClassKind() {
        return mUnitClassKind;
    }

    public void setUnitClassKind(String unitClassKind) {
        mUnitClassKind = unitClassKind;
    }

    public String getUnitProductivityString() {
        if (mUnitProductivity >= 0) {
            return String.format("%.0f", mUnitProductivity);
        } else {
            return "?";
        }
    }

    public double getUnitProductivity() {
        return mUnitProductivity;
    }

    public void setUnitProductivity(double unitProductivity) {
        mUnitProductivity = unitProductivity;
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

    public boolean isWorkersInHoliday() {
        return mWorkersInHoliday;
    }

    public void setWorkersInHoliday(boolean isWorkersInHoliday) {
        mWorkersInHoliday = isWorkersInHoliday;
    }
}
