package com.virtaandroidbuddy.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(primaryKeys = {"realm", "id"})
public class Company {

    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "name")
    private String mName;

    @ColumnInfo(name = "president_user_id")
    private String mPresidentUserId;

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

    public void setId(String mId) {
        this.mId = mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String mName) {
        this.mName = mName;
    }

    public String getPresidentUserId() {
        return mPresidentUserId;
    }

    public void setPresidentUserId(String mPresidentUserId) {
        this.mPresidentUserId = mPresidentUserId;
    }

}
