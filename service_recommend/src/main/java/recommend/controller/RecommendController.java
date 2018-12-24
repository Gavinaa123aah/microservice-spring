package recommend.controller;

import org.springframework.web.bind.annotation.*;
import recommend.service.SparkService;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Auther: wangming
 * @Date: 2018/12/19 17:09
 * @Description:
 */


@RestController
@RequestMapping("/recommend")
public class RecommendController {


    @RequestMapping(value = "/userRestaurantList", method = RequestMethod.GET)
    @ResponseBody
    public Object restaurantList(HttpServletRequest request) throws Exception {


        Map<String,Object> map = new HashMap<>();
        String user_id = request.getParameter("user_id");
        //cY500GQd9higRFc5aHRUgg 23132
        //SRUnaCILxxenBYTKgEmFRw 24577
        //CBwrmyZtdnpEpAh4En2pSQ 14349
        //fHr-AD4PYfHQs9-z9bcU8w 15914
        //fX8V9Qi_MErlmtiuEZxHkA 42854
        List<String> restaurantList = SparkService.recommendRestaurantForUser(user_id);
        map.put("restaurantList",restaurantList);

        return map;
    }


}
