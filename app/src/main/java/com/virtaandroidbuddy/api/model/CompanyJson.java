package com.virtaandroidbuddy.api.model;

import java.io.Serializable;

public class CompanyJson implements Serializable {

    private String mId;
    private String mName;
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
        return "CompanyJson{" +
                "id='" + mId + '\'' +
                ", name='" + mName + '\'' +
                ", presidentUserId='" + mPresidentUserId + '\'' +
                '}';
    }
}
