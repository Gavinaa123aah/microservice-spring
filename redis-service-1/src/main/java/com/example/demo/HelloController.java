package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import java.util.concurrent.TimeUnit;

@RestController
public class HelloController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @RequestMapping(value = "/redis", method = RequestMethod.GET)
    public String index(@RequestParam("name") String name) {
        String re;
        String res = stringRedisTemplate.opsForValue().get(name);
        if(res==null){
            re = "False";
        }else{
            stringRedisTemplate.opsForValue().set(name, "100",60*5, TimeUnit.SECONDS);//向redis里存入数据和设置缓存时间
            re = "True";
        }
        return re;
    }
}
