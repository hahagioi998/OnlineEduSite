package com.levy.edu.service.client;

import com.levy.edu.entity.UcenterMember;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletRequest;

/**
 * @description：服务调用接口
 * @author：LevyXie
 * @create：2022-03-27 10:18
 */
@Component
@FeignClient("service-order")
public interface OrderClient {
    @GetMapping("/eduorder/order/payFlag/{courseId}/{memberId}")
    public boolean getPayStatus(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
