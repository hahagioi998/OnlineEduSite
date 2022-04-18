package com.levy.service_order.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.github.wxpay.sdk.WXPayUtil;
import com.levy.service_order.entity.TOrder;
import com.levy.service_order.entity.TPayLog;
import com.levy.service_order.mapper.TPayLogMapper;
import com.levy.service_order.service.TOrderService;
import com.levy.service_order.service.TPayLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levy.service_order.utils.HttpClientUtils;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 支付日志表 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
@Service
public class TPayLogServiceImpl extends ServiceImpl<TPayLogMapper, TPayLog> implements TPayLogService {

    @Autowired
    private TOrderService orderService;

    @Override
    public HashMap<String, Object> createPayPic(String orderNo) {
        //根据订单号查询订单
        QueryWrapper<TOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no", orderNo);
        TOrder order = orderService.getOne(wrapper);

        //采用map设置微信支付相关参数
        HashMap<String, String> map = new HashMap<>();
        map.put("appid", "wx74862e0dfcf69954");
        map.put("mch_id", "1558950191");
        map.put("nonce_str", WXPayUtil.generateNonceStr());
        map.put("body", order.getCourseTitle());//商品名
        map.put("out_trade_no", orderNo);//订单号
        map.put("total_fee", order.getTotalFee().multiply(new BigDecimal("100")).longValue()+"");//价格
        map.put("spbill_create_ip", "127.0.0.1");
        map.put("notify_url", "http://guli.shop/api/order/weixinPay/weixinNotify\n");
        map.put("trade_type", "NATIVE");//支付类型：根据价格生成二维码

        //通过HttpClient访问微信的固定url
        HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/unifiedorder");
        //利用微信官方的工具，将map加密并以xml格式传递过去
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(map,"T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            //发送请求
            client.post();
            //获取返回的内容，为二维码
            String xml = client.getContent();
            Map<String, String> picMap = WXPayUtil.xmlToMap(xml);

            //二次封装
            HashMap<String, Object> resultMap = new HashMap<>();
            resultMap.put("out_trade_no", orderNo);
            resultMap.put("course_id", order.getCourseId());
            resultMap.put("total_fee", order.getTotalFee().toString());
            resultMap.put("result_code", picMap.get("result_code"));//操作状态码
            resultMap.put("code_url", picMap.get("code_url"));//二维码地址

            return resultMap;
        } catch (Exception e) {
            throw new GuliException(20001, "微信支付接口调用失败");
        }
    }

    //查询订单支付状态
    @Override
    public Map<String, String> queryStatus(String orderNo) {
        //封装请求参数
        Map map = new HashMap<>();
        map.put("appid", "wx74862e0dfcf69954");
        map.put("mch_id", "1558950191");
        map.put("out_trade_no", orderNo);
        map.put("nonce_str", WXPayUtil.generateNonceStr());

        //2、设置请求
        HttpClientUtils client = new HttpClientUtils("https://api.mch.weixin.qq.com/pay/orderquery");
        try {
            client.setXmlParam(WXPayUtil.generateSignedXml(map, "T6m9iK73b0kn9g5v426MKfHQH7X8rKwb"));
            client.setHttps(true);
            client.post();

            String xml = client.getContent();
            Map<String,String> resultMap = WXPayUtil.xmlToMap(xml);
            return resultMap;
        } catch (Exception e) {
            throw new GuliException(20001, "查询失败");
        }
    }

    //更新支付记录表和订单表
    @Override
    public void updateOrderLog(Map map) {
        String orderNo = (String) map.get("out_trade_no");
        //查询order表，先查后改
        QueryWrapper<TOrder> oWrapper = new QueryWrapper<>();
        oWrapper.eq("order_no", orderNo);
        TOrder order = orderService.getOne(oWrapper);
        if(order.getStatus() == 1){
            return;
        }
        order.setStatus(1);
        orderService.updateById(order);

        //添加支付记录
        TPayLog payLog = new TPayLog();
        payLog.setOrderNo(order.getOrderNo());//支付订单号
        payLog.setPayTime(new Date());
        payLog.setPayType(1);//支付类型
        payLog.setTotalFee(order.getTotalFee());//总金额(分)
        payLog.setTradeState(map.get("trade_state").toString());//支付状态
        payLog.setTransactionId(map.get("transaction_id").toString());
        payLog.setAttr(JSONObject.toJSONString(map));
        baseMapper.insert(payLog);//插入到支付日志表

    }
}
