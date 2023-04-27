package com.androidexam.stayfinder.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class RoomType implements Serializable {
    private int id;
    @SerializedName("roomTypeName")
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
