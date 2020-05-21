package com.virtaandroidbuddy.data.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"realm", "id"})
public class City {
    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "region_id")
    private String mRegionId;

    @ColumnInfo(name = "country_id")
    private String mCountryId;

    @ColumnInfo(name = "name")
    private String mName;

    @Override
    public String toString() {
        return mName;
    }

    public City() {
    }

    @Ignore
    public City(String name) {
        mName = name;
    }

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
}
