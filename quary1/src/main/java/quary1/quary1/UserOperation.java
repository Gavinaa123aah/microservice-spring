package quary1.quary1;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("RedisService")
public interface UserOperation {

    @RequestMapping("/redis")
    public String redis(@RequestParam("name") String name);
}
