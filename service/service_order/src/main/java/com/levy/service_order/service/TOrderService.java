package com.levy.service_order.service;

import com.levy.service_order.entity.TOrder;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 订单 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
public interface TOrderService extends IService<TOrder> {

    String saveOrder(String courseId, String memberId);
}
