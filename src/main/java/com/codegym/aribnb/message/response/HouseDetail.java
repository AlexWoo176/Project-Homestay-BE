package com.codegym.aribnb.message.response;

import javax.persistence.Lob;

public class HouseDetail {
    private Long id;
    private String name;
    private String catName;
    @Lob
    private String picture;
    private String address;
    private String bedroomNumber;
    private String bathroomNumber;
    private String description;
    private String price;
    private String area;
    private String userName;
    private String userId;
    private String status;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(String bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public String getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(String bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
