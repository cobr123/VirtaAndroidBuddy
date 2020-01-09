package com.virtaandroidbuddy.ui.login;


import com.google.gson.annotations.SerializedName;

public class LoginFormJson {
    @SerializedName("login")
    private String mLogin;
    @SerializedName("password")
    private String mPassword;
    @SerializedName("realm")
    private String mRealm;

    public LoginFormJson() {
    }

    public LoginFormJson(String login, String password, String realm) {
        this.mLogin = login;
        this.mPassword = password;
        this.mRealm = realm;
    }

    public String getLogin() {
        return mLogin;
    }

    public void setLogin(String login) {
        this.mLogin = login;
    }

    public String getPassword() {
        return mPassword;
    }

    public void setPassword(String password) {
        this.mPassword = password;
    }

    public String getRealm() {
        return mRealm;
    }

    public void setRealm(String realm) {
        this.mRealm = realm;
    }
}
