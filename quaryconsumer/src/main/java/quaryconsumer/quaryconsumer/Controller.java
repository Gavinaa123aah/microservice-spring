package quaryconsumer.quaryconsumer;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import quaryconsumer.quaryconsumer.Service.QuaryService;

import java.util.List;

@RestController
public class Controller {

    @Autowired
    QuaryService qs;

    @RequestMapping("/kq")
    public List<Information> keyquary(@RequestParam("username") String username, @RequestParam("key") String key){

        return qs.disquary(username,key);
    }

    @RequestMapping("cq")
    public List<Information> conquary(@RequestParam("key") String key,
                                      @RequestParam("username") String name,
                                      @RequestParam("address") String addr,
                                      @RequestParam("stars") double stars,
                                      @RequestParam("city") String city,
                                      @RequestParam("state") String state){

        return qs.conquary(key,name,addr,stars,city,state);
    }

}
