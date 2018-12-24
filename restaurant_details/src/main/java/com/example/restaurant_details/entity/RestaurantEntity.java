package com.example.restaurant_details.entity;

import java.io.Serializable;

public class RestaurantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String userName;
    private String passWord;

    public RestaurantEntity() {
        super();
    }

    public RestaurantEntity(String userName, String passWord) {
        super();
        this.passWord = passWord;
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    @Override
    public String toString() {
        return "id=" + id +
                ", userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' ;
    }
}
