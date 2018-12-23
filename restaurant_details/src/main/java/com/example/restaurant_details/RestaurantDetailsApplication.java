package com.example.restaurant_details;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.example.restaurant_details.mapper")
public class RestaurantDetailsApplication {

    public static void main(String[] args) {
        SpringApplication.run(RestaurantDetailsApplication.class, args);
    }

}

