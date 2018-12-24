package com.example.restaurant_details.mapper;

import com.example.restaurant_details.entity.RestaurantEntity;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("restaurantMapper")
public interface RestaurantMapper {
	
	List<RestaurantEntity> getAll();
	
	RestaurantEntity findById(Long id);

	int insert(RestaurantEntity restaurantEntity);

	void update(RestaurantEntity restaurantEntity);

	void delete(Long id);

}