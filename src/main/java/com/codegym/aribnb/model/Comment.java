package com.codegym.aribnb.model;

import javax.persistence.*;

@Entity
public class Comment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn
    private User user;

    @Column(columnDefinition = "long")
    private String comment;

    @ManyToOne
    @JoinColumn
    private HouseEntity house;

    public Comment() {
    }

    public Comment(Long id, User user, String comment, HouseEntity house) {
        this.id = id;
        this.user = user;
        this.comment = comment;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public HouseEntity getHouse() {
        return house;
    }

    public void setHouse(HouseEntity house) {
        this.house = house;
    }
}
