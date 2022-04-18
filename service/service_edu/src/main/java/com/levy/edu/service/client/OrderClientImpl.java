package com.levy.edu.service.client;

import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * @description：服务熔断处理
 * @author：LevyXie
 * @create：2022-03-27 10:19
 */
@Component
public class OrderClientImpl implements OrderClient{
    @Override
    public boolean getPayStatus(String courseId, String memberId) {
        throw new GuliException(20001, "获取订单状态异常");
    }
}
