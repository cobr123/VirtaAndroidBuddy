package com.virtaandroidbuddy.data.database.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Session {

    @PrimaryKey
    @ColumnInfo(name = "id")
    private int mId;

    @ColumnInfo(name = "realm")
    private String mRealm;

    @ColumnInfo(name = "company_id")
    private String mCompanyId;

    @ColumnInfo(name = "company_name")
    private String mCompanyName;

    @ColumnInfo(name = "user_id")
    private String mUserId;

    public Session() {
    }

    @Ignore
    public Session(String realm, String companyId, String companyName, String userId) {
        this.mId = 1;
        this.mRealm = realm;
        this.mCompanyId = companyId;
        this.mCompanyName = companyName;
        this.mUserId = userId;
    }

    public int getId() {
        return mId;
    }

    public void setId(int id) {
        mId = id;
    }

    public String getRealm() {
        return mRealm;
    }

    public void setRealm(String realm) {
        mRealm = realm;
    }

    public String getCompanyId() {
        return mCompanyId;
    }

    public void setCompanyId(String companyId) {
        mCompanyId = companyId;
    }

    public String getCompanyName() {
        return mCompanyName;
    }

    public void setCompanyName(String companyName) {
        mCompanyName = companyName;
    }

    public String getUserId() {
        return mUserId;
    }

    public void setUserId(String userId) {
        mUserId = userId;
    }
}
