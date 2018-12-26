package quary1.quary1.Controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quary1.quary1.InformationBean.Information;
import quary1.quary1.Service.QuaryDAO;
import quary1.quary1.UserOperation;

import java.util.List;

@RestController
public class QuaryController {

    @Autowired
    QuaryDAO cd;

    @Autowired
    UserOperation uo;

    @RequestMapping("/kq")
    public List<Information> disquary(@RequestParam("username") String username,@RequestParam("key") String key){
//        if (uo.redis(username).equals("True"))
            return cd.getBaseInfoByKey(key);
//        else
//            return null;
    }

    @RequestMapping("/cq")
    public List<Information> conquary(@RequestParam("key") String key,
                                      @RequestParam("username") String name,
                                      @RequestParam("address") String addr,
                                      @RequestParam("stars") double stars,
                                      @RequestParam("city") String city,
                                      @RequestParam("state") String state){
//        if (uo.redis(name).equals("True"))
         return cd.getBaseInfoBycon(key,addr,state,stars,city);
//        else
//            return null;
    }
}
