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

    public Session() {
    }

    @Ignore
    public Session(int mId, String mRealm, String mCompanyId) {
        this.mId = mId;
        this.mRealm = mRealm;
        this.mCompanyId = mCompanyId;
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
}
