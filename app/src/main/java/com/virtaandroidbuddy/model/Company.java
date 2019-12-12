package com.virtaandroidbuddy.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

@Entity
public class Company implements Serializable {

    @PrimaryKey
    @ColumnInfo(name = "id")
    @SerializedName("id")
    private String mId;

    @ColumnInfo(name = "name")
    @SerializedName("name")
    private String mName;

    @ColumnInfo(name = "president_user_id")
    @SerializedName("president_user_id")
    private String mPresidentUserId;

    public String getmId() {
        return mId;
    }

    public void setmId(String mId) {
        this.mId = mId;
    }

    public String getmName() {
        return mName;
    }

    public void setmName(String mName) {
        this.mName = mName;
    }

    public String getmPresidentUserId() {
        return mPresidentUserId;
    }

    public void setmPresidentUserId(String mPresidentUserId) {
        this.mPresidentUserId = mPresidentUserId;
    }

    @Override
    public String toString() {
        return "Company{" +
                "mId='" + mId + '\'' +
                ", mName='" + mName + '\'' +
                ", mPresidentUserId='" + mPresidentUserId + '\'' +
                '}';
    }
}
