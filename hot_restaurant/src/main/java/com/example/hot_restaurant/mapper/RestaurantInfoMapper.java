package com.example.hot_restaurant.mapper;

import com.example.hot_restaurant.entity.RestaurantInfo;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository("/restaurantInfoMapper")
public interface RestaurantInfoMapper {
     List<RestaurantInfo> getHotRestaurant();

    public List<RestaurantInfo> recommend_restaurant(int id);
}
