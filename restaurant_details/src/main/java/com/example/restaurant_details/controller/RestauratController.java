package com.example.restaurant_details.controller;

import com.example.restaurant_details.entity.RestaurantEntity;
import com.example.restaurant_details.mapper.RestaurantMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurant")
public class RestauratController {
    @Autowired
    RestaurantMapper restaurantMapper;

    @RequestMapping(value={"/findById"}, method= RequestMethod.POST)
    public Object selectById(String id){
        Map<String,Object> map = new HashMap<>();
        RestaurantEntity restaurantEntity = null;
        try {
            restaurantEntity = restaurantMapper.findById(Long.valueOf(id));
            map.put("status","success");
            map.put("code","200");
            map.put("result",restaurantEntity);
            return map;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            map.put("status","fail");
            map.put("code","500");
            return map;
        }

    }

    @RequestMapping(value={"/findAll"}, method= RequestMethod.POST)
    public Object getAll(){
        Map<String,Object> map = new HashMap<>();
        try {
            List<RestaurantEntity> restaurantEntityList = restaurantMapper.getAll();
            map.put("status","success");
            map.put("code","200");
            map.put("result",restaurantEntityList);
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","fail");
            map.put("code","500");
            return map;
        }
    }

    @RequestMapping(value={"/add"}, method= RequestMethod.POST)
    public Object add(RestaurantEntity restaurantEntity){
        Map<String, Object> map = new HashMap<>();
        try {
            restaurantMapper.insert(restaurantEntity);
            map.put("status","success");
            map.put("code","200");
            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("status","fail");
            map.put("code","500");
            return map;
        }

    }
}
