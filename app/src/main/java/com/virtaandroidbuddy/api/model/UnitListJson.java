package com.virtaandroidbuddy.api.model;


import java.io.Serializable;

public class UnitListJson implements Serializable {

    private String mId;
    private String mName;

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
