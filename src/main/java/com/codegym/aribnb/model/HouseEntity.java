package com.codegym.aribnb.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "house")
public class HouseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String houseName;
    private Long category;

    @Lob
    private String picture;

    @OneToMany(targetEntity = OrderHouse.class)
    @JsonManagedReference
    private List<OrderHouse> orderHouses;

    private String address;
    private Long bedroomNumber;
    private Long bathroomNumber;

    @Column(columnDefinition = "long")
    private String description;

    private Long price;
    private Long rate;
    private Long area;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Column(name = "host_id")
    private Long user;

    public HouseEntity() {
    }

    public HouseEntity(String houseName, Long category, String picture, String address, Long bedroomNumber, Long bathroomNumber, String description, Long price, Long rate, Long area, Status status, Long user) {
        this.houseName = houseName;
        this.category = category;
        this.picture = picture;
        this.address = address;
        this.bedroomNumber = bedroomNumber;
        this.bathroomNumber = bathroomNumber;
        this.description = description;
        this.price = price;
        this.rate = rate;
        this.area = area;
        this.status = status;
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public List<OrderHouse> getOrderHouses() {
        return orderHouses;
    }

    public void setOrderHouses(List<OrderHouse> orderHouses) {
        this.orderHouses = orderHouses;
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

    public Long getRate() {
        return rate;
    }

    public void setRate(Long rate) {
        this.rate = rate;
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
