package com.example.demo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

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

            String sql = "SELECT count(*) from users WHERE username =" + name+"";
            int res = (Integer) jdbcTemplate.queryForObject(sql,int.class);
            if(res>0){
                return "用户名已存在！";
            }else{
                String sql1 = "Insert into users(username,password) values("+name+","+password+")";
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
            String sql = "select password from users where username = " + name+"";
            String pass = (String)jdbcTemplate.queryForObject(sql,java.lang.String.class);
            if(password.equals('"'+pass+'"')){
                redisService.insertredis(name);
                return "True";
            }
            else{
                return "False";
            }

        }

    }
}
