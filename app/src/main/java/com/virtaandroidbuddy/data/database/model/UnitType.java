package com.virtaandroidbuddy.data.database.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;

@Entity(primaryKeys = {"realm", "id"})
public class UnitType {
    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "id")
    private String mId;

    @ColumnInfo(name = "unit_class_id")
    private String mUnitClassId;

    @ColumnInfo(name = "name")
    private String mName;

    @Override
    public String toString() {
        return mName;
    }

    public UnitType() {
    }

    @Ignore
    public UnitType(String name) {
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

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getUnitClassId() {
        return mUnitClassId;
    }

    public void setUnitClassId(String unitClassId) {
        mUnitClassId = unitClassId;
    }
}
