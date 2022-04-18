package com.levy.service_order.service;

import com.levy.service_order.entity.TPayLog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
public interface TPayLogService extends IService<TPayLog> {

    HashMap<String, Object> createPayPic(String orderNo);

    Map<String, String> queryStatus(String orderNo);

    void updateOrderLog(Map map);
}
