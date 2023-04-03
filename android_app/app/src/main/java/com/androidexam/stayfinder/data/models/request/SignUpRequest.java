package com.androidexam.stayfinder.data.models.request;

import com.google.gson.annotations.SerializedName;

import java.io.File;

public class SignUpRequest {
    @SerializedName("username")
    private String username;
    @SerializedName("password")
    private String password;
    @SerializedName("name")
    private String name;
    @SerializedName("file")
    private File file;

    public SignUpRequest(String username, String password, String name, File file) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.file = file;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }
}
