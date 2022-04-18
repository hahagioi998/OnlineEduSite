package com.levy.service_order.controller;


import com.levy.commonutils.R;
import com.levy.service_order.service.TPayLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
@RestController
@CrossOrigin
@RequestMapping("/eduorder/paylog")
public class TPayLogController {
    @Autowired
    private TPayLogService payLogService;

    //根据订单号生成二维码
    @GetMapping("/createPayPic/{orderNo}")
    public R createPayPic(@PathVariable String orderNo){
        HashMap<String, Object> map = payLogService.createPayPic(orderNo);
        System.out.println(map);
        return R.ok().data(map);
    }
    //查询订单支付状态
    @GetMapping("/checkPayStatus/{orderNo}")
    public R checkPayStatus(@PathVariable String orderNo){
        Map<String, String> map = payLogService.queryStatus(orderNo);
        System.out.println(map);
        if(null == map){
            return R.error().message("支付出错了！");
        }
        if(map.get("trade_state").equals("SUCCESS")){
            payLogService.updateOrderLog(map);
            return R.ok().message("支付成功");
        }
        return R.ok().code(25000).message("支付中");
    }
}

