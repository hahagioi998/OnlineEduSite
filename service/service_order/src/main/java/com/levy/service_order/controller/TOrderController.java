package com.levy.service_order.controller;


import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.levy.commonutils.JwtUtils;
import com.levy.commonutils.R;
import com.levy.service_order.entity.TOrder;
import com.levy.service_order.service.TOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 订单 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
@RestController
@CrossOrigin
@RequestMapping("/eduorder/order")
public class TOrderController {

    @Autowired
    private TOrderService orderService;

    @GetMapping("/createOrder/{courseId}")
    public R createOrder(@PathVariable String courseId, HttpServletRequest request){
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        if(StringUtils.isEmpty(memberId)){
            return R.error().message("用户未登录");
        }
        String orderNo = orderService.saveOrder(courseId, memberId);
        return R.ok().data("orderNo", orderNo);
    }

    @GetMapping("/getOrderInfo/{orderNo}")
    public R getOrder(@PathVariable String orderNo){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder order = orderService.getOne(wrapper);
        return R.ok().data("order",order);
    }

    @GetMapping("/payFlag/{courseId}/{memberId}")
    public boolean getPayStatus(@PathVariable String courseId,@PathVariable String memberId){
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("member_id",memberId);
        wrapper.eq("course_id",courseId);
        wrapper.eq("status",1);
        long count = orderService.count(wrapper);
        return count > 0;
    }


}

