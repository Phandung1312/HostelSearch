package com.androidexam.stayfinder.data.models.request;

import com.androidexam.stayfinder.data.models.Post;

public class HostelRequest {
    private String name;
    private int capacity;
    private double area;
    private String address;
    private double rentPrice;
    private double depositPrice;
    private int status;
    private String description;
    private int roomTypeId;
    private double electricPrice;
    private double waterPrice;
    private PostRequest post;

    public HostelRequest(String name, int capacity, double area, String address, double rentPrice, double depositPrice, int status, String description, int roomTypeId, double electricPrice, double waterPrice, PostRequest post) {
        this.name = name;
        this.capacity = capacity;
        this.area = area;
        this.address = address;
        this.rentPrice = rentPrice;
        this.depositPrice = depositPrice;
        this.status = status;
        this.description = description;
        this.roomTypeId = roomTypeId;
        this.electricPrice = electricPrice;
        this.waterPrice = waterPrice;
        this.post = post;
    }

    public String getName() {
        return name;
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

    public int getRoomType() {
        return roomTypeId;
    }

    public void setRoomType(int roomTypeId) {
        this.roomTypeId = roomTypeId;
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

    public PostRequest getPost() {
        return post;
    }

    public void setPost(PostRequest post) {
        this.post = post;
    }
}
