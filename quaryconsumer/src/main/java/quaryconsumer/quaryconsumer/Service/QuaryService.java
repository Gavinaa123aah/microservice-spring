package quaryconsumer.quaryconsumer.Service;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import quaryconsumer.quaryconsumer.Information;

import java.util.List;

@FeignClient("quary-service")
public interface QuaryService {

    @RequestMapping("/kq")
    public List<Information> disquary(@RequestParam("username") String username, @RequestParam("key") String key);

    @RequestMapping("/cq")
    public List<Information> conquary(@RequestParam("key") String key,
                                      @RequestParam("username") String name,
                                      @RequestParam("address") String addr,
                                      @RequestParam("stars") double stars,
                                      @RequestParam("city") String city,
                                      @RequestParam("state") String state);
}
