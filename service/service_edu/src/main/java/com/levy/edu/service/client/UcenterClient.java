package com.levy.edu.service.client;

import com.levy.edu.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @description：服务调用接口
 * @author：LevyXie
 * @create：2022-03-24 11:33
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping(value = "/educenter/member/getMemberById/{id}")
    public UcenterMember getMemberById(@PathVariable String id);
}
