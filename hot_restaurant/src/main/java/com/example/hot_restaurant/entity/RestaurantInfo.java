package com.example.hot_restaurant.entity;

public class RestaurantInfo {
    private int id;
    private String business_id;
    private String name;
    private Double stars;

    public int getId() {
        return id;
    }

    public void setId(int id) {
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

    public Double getStars() {
        return stars;
    }

    public void setStars(Double stars) {
        this.stars = stars;
    }

    @Override
    public String toString() {
        return "RestaurantInfo{" +
                "id=" + id +
                ", business_id='" + business_id + '\'' +
                ", name='" + name + '\'' +
                ", stars=" + stars +
                '}';
    }


}
