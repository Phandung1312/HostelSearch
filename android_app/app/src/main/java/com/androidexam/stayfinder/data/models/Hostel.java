package com.androidexam.stayfinder.data.models;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Hostel implements Serializable {
    private int id;
    private String name;
    private int capacity;
    private double area;
    private String address;
    private double rentPrice;
    private double depositPrice;
    private int status;
    private String description;
    private RoomType roomType;
    private double electricPrice;
    private double waterPrice;
    private List<Image> images;
    private Post post;

    public Hostel(Hostel hostel) {
        this.id = hostel.id;
        this.name = hostel.name;
        this.capacity = hostel.capacity;
        this.area = hostel.area;
        this.address = hostel.address;
        this.rentPrice = hostel.rentPrice;
        this.depositPrice = hostel.depositPrice;
        this.status = hostel.status;
        this.description = hostel.description;
        this.roomType = hostel.roomType;
        this.electricPrice = hostel.electricPrice;
        this.waterPrice = hostel.waterPrice;
        this.images = hostel.images;
        this.post = hostel.post;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCapacity() {
        return capacity;
    }

    public void setCapacity(int capacity) {
        this.capacity = capacity;
    }

    public double getArea() {
        return area;
    }

    public void setArea(double area) {
        this.area = area;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public double getRentPrice() {
        return rentPrice;
    }

    public void setRentPrice(double rentPrice) {
        this.rentPrice = rentPrice;
    }

    public double getDepositPrice() {
        return depositPrice;
    }

    public void setDepositPrice(double depositPrice) {
        this.depositPrice = depositPrice;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public RoomType getRoomType() {
        return roomType;
    }

    public void setRoomType(RoomType roomType) {
        this.roomType = roomType;
    }

    public double getElectricPrice() {
        return electricPrice;
    }

    public void setElectricPrice(double electricPrice) {
        this.electricPrice = electricPrice;
    }

    public double getWaterPrice() {
        return waterPrice;
    }

    public void setWaterPrice(double waterPrice) {
        this.waterPrice = waterPrice;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

}
