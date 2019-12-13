package com.virtaandroidbuddy.api.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class UnitJson implements Serializable {

    @SerializedName("id")
    private String mId;

    @SerializedName("name")
    private String mName;

    public UnitJson(String mId, String mName) {
        this.mId = mId;
        this.mName = mName;
    }

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
