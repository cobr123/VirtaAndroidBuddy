package com.virtaandroidbuddy.data.api.model;


import java.io.Serializable;

public class UnitListDataJson implements Serializable {
    private String mId;
    private String mName;
    private String mUnitTypeId;
    private String mUnitTypeSymbol;
    private String mUnitTypeName;
    private String mCountryId;
    private String mCountrySymbol;
    private String mCountryName;
    private String mRegionId;
    private String mRegionName;
    private String mCityId;
    private String mCityName;

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
}