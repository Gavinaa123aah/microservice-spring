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


    @RequestMapping(value = "/hotRestaurantList", method = RequestMethod.GET)
    @ResponseBody
    public Object restaurantList(HttpServletRequest request) throws Exception {


        Map<String,Object> map = new HashMap<>();

//        List<String> restaurantList = new ArrayList<>();
        List<String> restaurantList = SparkService.recommendHotRestaurant();
//        for(int i = 0; i < 100; i ++ ){
//            restaurantList.add("restaurant" + i);
//        }

        map.put("restaurantList", restaurantList);

        return map;
    }


}
