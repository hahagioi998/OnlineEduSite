package com.levy.service_statistics.service.client;

import com.levy.commonutils.R;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

/**
 * @description：熔断机制
 * @author：LevyXie
 * @create：2022-03-27 15:48
 */
@Component
public class UcenterClientImpl implements UcenterClient{
    @Override
    public R getRegisterDaily(String day) {
        throw new GuliException(20001, "查询失败");
    }
}
