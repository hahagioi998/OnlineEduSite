package com.levy.service_order.service.client;

import com.levy.commonutils.orderVo.CourseOrderVo;
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
@FeignClient(name = "service-edu",fallback = CourseClientImpl.class)
public interface CourseClient {
    @RequestMapping("/eduservice/course/getCourseOrder/{id}")
    public CourseOrderVo getCourseOrder(@PathVariable("id") String id);
}
