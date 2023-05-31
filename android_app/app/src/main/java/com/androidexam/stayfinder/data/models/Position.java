package com.androidexam.stayfinder.data.models;

import com.google.gson.annotations.SerializedName;

public class Position {
    @SerializedName("id")
    private int id;
    @SerializedName("positionName")
    private String posName;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getPosName() {
        return posName;
    }

    public void setPosName(String posName) {
        this.posName = posName;
    }
}
