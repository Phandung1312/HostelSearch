package com.androidexam.stayfinder.data.models.request;

import com.google.gson.annotations.SerializedName;

public class SignUpRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("avatar")
    private String avatar;

    public SignUpRequest(String username, String password, String name, String avatar) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.avatar = avatar;
    }
}
