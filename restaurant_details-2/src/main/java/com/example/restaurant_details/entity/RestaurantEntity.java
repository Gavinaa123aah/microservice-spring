package com.example.restaurant_details.entity;

import java.io.Serializable;

public class RestaurantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long id;
    private String business_id;
    private String name;
    private String stars;
    private String city;
    private String address;

    public RestaurantEntity() {
        super();
    }

    public RestaurantEntity(String userName, String passWord) {
        super();

        this.name = passWord;
        this.stars = userName;
    }

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBusiness_id() {
        return business_id;
    }

    public void setBusiness_id(String business_id) {
        this.business_id = business_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStars() {
        return stars;
    }

    public void setStars(String stars) {
        this.stars = stars;
    }


    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "RestaurantEntity{" +
                "id=" + id +
                ", business_id='" + business_id + '\'' +
                ", name='" + name + '\'' +
                ", stars='" + stars + '\'' +
                ", city='" + city + '\'' +
                ", address='" + address + '\'' +
                '}';
    }
}
