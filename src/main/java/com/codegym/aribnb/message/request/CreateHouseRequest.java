package com.codegym.aribnb.message.request;

import com.codegym.aribnb.model.HouseEntity;
import com.codegym.aribnb.model.Status;

public class CreateHouseRequest {
    private String houseName;
    private Long category;
    private String picture;
    private String address;
    private Long bedroomNumber;
    private Long bathroomNumber;
    private String description;
    private Long price;
    private Long area;
    private Status status;
    private Long user;

    public HouseEntity cloneToEntity() {
        HouseEntity houseEntity = new HouseEntity();
        houseEntity.setHouseName(houseName);
        houseEntity.setCategory(category);
        houseEntity.setPicture(picture);
        houseEntity.setAddress(address);
        houseEntity.setBathroomNumber(bathroomNumber);
        houseEntity.setBedroomNumber(bedroomNumber);
        houseEntity.setDescription(description);
        houseEntity.setPrice(price);
        houseEntity.setArea(area);
        houseEntity.setStatus(Status.AVAILABLE);
        houseEntity.setUser(user);
        return  houseEntity;
    }

    public String getHouseName() {
        return houseName;
    }

    public void setHouseName(String houseName) {
        this.houseName = houseName;
    }

    public Long getCategory() {
        return category;
    }

    public void setCategory(Long category) {
        this.category = category;
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

    public Long getBedroomNumber() {
        return bedroomNumber;
    }

    public void setBedroomNumber(Long bedroomNumber) {
        this.bedroomNumber = bedroomNumber;
    }

    public Long getBathroomNumber() {
        return bathroomNumber;
    }

    public void setBathroomNumber(Long bathroomNumber) {
        this.bathroomNumber = bathroomNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    public Long getArea() {
        return area;
    }

    public void setArea(Long area) {
        this.area = area;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Long getUser() {
        return user;
    }

    public void setUser(Long user) {
        this.user = user;
    }
}
