package com.virtaandroidbuddy.api.model;


import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Company implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    @SerializedName("president_user_id")
    private String mPresidentUserId;

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

    @Override
    public String toString() {
        return "Company{" +
                "id='" + mId + '\'' +
                ", name='" + mName + '\'' +
                ", presidentUserId='" + mPresidentUserId + '\'' +
                '}';
    }
}
