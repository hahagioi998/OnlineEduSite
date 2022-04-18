package com.levy.edu.service.client;

import com.levy.edu.entity.UcenterMember;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.stereotype.Component;

/**
 * @description：服务熔断处理
 * @author：LevyXie
 * @create：2022-03-24 11:33
 */
@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public UcenterMember getMemberById(String id) {
        throw new GuliException(20001, "登录状态错误");
    }
}
