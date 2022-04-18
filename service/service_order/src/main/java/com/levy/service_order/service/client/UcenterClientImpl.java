package com.levy.service_order.service.client;

import com.levy.commonutils.orderVo.UcenterMemberVo;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * @description：熔断处理
 * @author：LevyXie
 * @create：2022-03-24 11:33
 */
@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public UcenterMemberVo getMemberById(String id) {
        throw new GuliException(20001, "登录状态错误");
    }
}
