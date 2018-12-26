package com.example.demo;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@FeignClient("RedisService")
@Repository
public interface RedisService {
    @RequestMapping(value = "/insertredis", method = RequestMethod.GET)
//    @ResponseBody
    void insertredis(@RequestParam("name") String name);
}
