package com.levy.edu.service.client;

import com.levy.commonutils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description：服务调用接口
 * @author：LevyXie
 * @create：2022-03-16 10:56
 */
@Component
@FeignClient(name = "service-vod",fallback = VodClientImpl.class)
//配置微服务，并做好熔断机制处理
public interface VodClient {
    @DeleteMapping("/eduvod/video/delete/{id}")
    public R deleteVideo(@PathVariable("id") String id);
}
