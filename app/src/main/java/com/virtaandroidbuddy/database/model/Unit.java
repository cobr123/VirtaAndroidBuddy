package com.virtaandroidbuddy.database.model;

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

    @ColumnInfo(name = "company_id")
    private String mCompanyId;

    @ColumnInfo(name = "name")
    private String mName;

    public Unit() {
    }

    @Ignore
    public Unit(@NonNull String mRealm, @NonNull String mId, String mName, String companyId) {
        this.mRealm = mRealm;
        this.mId = mId;
        this.mName = mName;
        mCompanyId = companyId;
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
}
