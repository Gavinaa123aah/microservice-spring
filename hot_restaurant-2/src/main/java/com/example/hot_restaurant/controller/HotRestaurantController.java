package com.example.hot_restaurant.controller;

import com.example.hot_restaurant.entity.RestaurantInfo;
import com.example.hot_restaurant.mapper.RestaurantInfoMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommend")
public class HotRestaurantController {

    @Autowired
    RestaurantInfoMapper restaurantInfoMapper;

    @RequestMapping("/getHot")
    public Object getHot(){
        Map<String, Object> map = new HashMap<>();
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper.getHotRestaurant();
        for (RestaurantInfo restaurantInfo:
             restaurantInfoList) {

            System.out.println(restaurantInfo.getName());
        }
        map.put("code", 200);
        map.put("status", "success");
        map.put("numbers", restaurantInfoList.size());
        map.put("results",restaurantInfoList);
        return map;
    }

    @RequestMapping("getRecommandById")
    public Object getRecommandById(String id){
        Map<String, Object> map = new HashMap<>();
        List<RestaurantInfo> restaurantInfoList = restaurantInfoMapper.recommend_restaurant(Integer.valueOf(id));

        if (restaurantInfoList.size()!=0 ){
            map.put("result", restaurantInfoList);
            map.put("code", 200);
            map.put("status", "success");
            return map;
        }else {
            map.put("result", restaurantInfoList);
            map.put("numbers", restaurantInfoList.size());
            map.put("code", 201);
            map.put("status", "无该用户的推荐");
            return map;
        }
    }
}
