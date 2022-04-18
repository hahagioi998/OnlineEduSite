package com.levy.service_order.service.client;

import com.levy.commonutils.orderVo.CourseOrderVo;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * @description：熔断处理
 * @author：LevyXie
 * @create：2022-03-24 16:13
 */
@Component
public class CourseClientImpl implements CourseClient{
    @Override
    public CourseOrderVo getCourseOrder(String id) {
        throw new GuliException(20001, "获取课程信息失败");
    }
}
