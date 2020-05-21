package com.virtaandroidbuddy.data.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;

@Entity(primaryKeys = {"realm", "id"})
public class UnitClassKind {
    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "name")
    private String mName;

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
}
