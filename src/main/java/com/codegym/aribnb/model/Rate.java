package com.codegym.aribnb.model;

import javax.persistence.*;

@Entity
public class Rate {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn
    private User user;

    private Long ratePoint;

    @ManyToOne
    @JoinColumn
    private HouseEntity house;

    public Rate() {
    }

    public Rate(User user, Long ratePoint, HouseEntity house) {
        this.user = user;
        this.ratePoint = ratePoint;
        this.house = house;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getRatePoint() {
        return ratePoint;
    }

    public void setRatePoint(Long ratePoint) {
        this.ratePoint = ratePoint;
    }

    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity house) {
        this.house = house;
    }
}
