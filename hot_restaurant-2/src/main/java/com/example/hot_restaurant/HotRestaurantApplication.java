package com.example.hot_restaurant;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.hot_restaurant.mapper")
public class HotRestaurantApplication {

    public static void main(String[] args) {
        SpringApplication.run(HotRestaurantApplication.class, args);
    }

}

