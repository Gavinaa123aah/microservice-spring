package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.Random;

@RestController
public class IndexController {
    @Autowired
    private RedisService redisService;

    @Resource
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(value = "/register",method = RequestMethod.GET)
    public String register(@RequestParam("name") String name,@RequestParam("password") String password) {
        if(name.equals("") || password.equals("")){
            return "False";
        }else{
            String sql = "SELECT count(*) from userinfo WHERE name =" + name+"";
            int res = (Integer) jdbcTemplate.queryForObject(sql,int.class);
            if(res>0){
                return "用户名已存在！";
            }else{
                String sql1 = "Insert into userinfo(user_id,name,password) values("+(int)(1+Math.random()*999999)+","+name+","+password+")";
                System.out.println(sql1);
                jdbcTemplate.update(sql1);
                return "True";
            }

        }

    }


    @RequestMapping(value = "/login",method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam("name") String name,@RequestParam("password") String password) {
        if(name.equals("") || password.equals("")){
            return "False";
        }else{
            String sql = "select password from userinfo where name = " + name+"";
            String sql2 = "select user_id from userinfo where name = " + name+"";

            String pass = (String)jdbcTemplate.queryForObject(sql,java.lang.String.class);
            String user_id = (String)jdbcTemplate.queryForObject(sql2,java.lang.String.class);
            System.out.println(pass);
            if(password.equals(pass)){
                redisService.insertredis(name);
                return "True"+","+user_id;
            }
            else{
                return "False";
            }

        }

    }
}
