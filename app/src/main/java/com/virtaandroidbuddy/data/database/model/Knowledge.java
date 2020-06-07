package com.virtaandroidbuddy.data.database.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;


@Entity(primaryKeys = {"realm", "user_id", "kind"})
public class Knowledge {
    @NonNull
    @ColumnInfo(name = "realm")
    private String mRealm;

    @NonNull
    @ColumnInfo(name = "user_id")
    private String mUserId;

    @NonNull
    @ColumnInfo(name = "kind")
    private String mKind;

    @ColumnInfo(name = "base")
    private int mBase;

    @ColumnInfo(name = "bonus")
    private int mBonus;

    @ColumnInfo(name = "progress")
    private double mProgress;

    @ColumnInfo(name = "delta")
    private double mDelta;

    @ColumnInfo(name = "bonus_percent")
    private double mBonusPercent;

    @NonNull
    public String getRealm() {
        return mRealm;
    }

    public void setRealm(@NonNull String realm) {
        mRealm = realm;
    }

    @NonNull
    public String getUserId() {
        return mUserId;
    }

    public void setUserId(@NonNull String userId) {
        mUserId = userId;
    }

    @NonNull
    public String getKind() {
        return mKind;
    }

    public void setKind(@NonNull String kind) {
        mKind = kind;
    }

    public int getBase() {
        return mBase;
    }

    public void setBase(int base) {
        mBase = base;
    }

    public int getBonus() {
        return mBonus;
    }

    public void setBonus(int bonus) {
        mBonus = bonus;
    }

    public double getProgress() {
        return mProgress;
    }

    public void setProgress(double progress) {
        mProgress = progress;
    }

    public double getDelta() {
        return mDelta;
    }

    public void setDelta(double delta) {
        mDelta = delta;
    }

    public double getBonusPercent() {
        return mBonusPercent;
    }

    public void setBonusPercent(double bonusPercent) {
        mBonusPercent = bonusPercent;
    }

    public String getLvlString() {
        if (getBonus() > 0) {
            return getBase() + "+" + getBonus();
        } else {
            return String.valueOf(getBase());
        }
    }

    public String getProgressString() {
        return String.format("%.2f", getProgress()) + "% (+" + String.format("%.2f", getDelta()) + "%)";
    }

}
