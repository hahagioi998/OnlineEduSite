package com.levy.service_order.service.client;

import com.levy.commonutils.orderVo.UcenterMemberVo;
import com.levy.service_order.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @description：服务调用
 * @author：LevyXie
 * @create：2022-03-24 11:33
 */
@Component
@FeignClient(name = "service-ucenter",fallback = UcenterClientImpl.class)
public interface UcenterClient {
    @GetMapping(value = "/educenter/member/getMemberById/{id}")
    public UcenterMemberVo getMemberById(@PathVariable("id") String id);
}
