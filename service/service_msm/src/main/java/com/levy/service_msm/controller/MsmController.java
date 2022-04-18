package com.levy.service_msm.controller;

import com.levy.commonutils.R;
import com.levy.commonutils.RandomUtil;
import com.levy.service_msm.service.MsmService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @description：短信前端控制器
 * @author：LevyXie
 * @create：2022-03-20 15:14
 */
@RestController
@CrossOrigin
@RequestMapping("/edumsm/msm")
public class MsmController {

    @Autowired
    private MsmService msmService;

    @Autowired
    private RedisTemplate<String,String> redisTemplate;

    @GetMapping("/sendMessage/{phone}")
    public R sendMessage(@PathVariable String phone){
        //本功能同时实现验证码的确认和发送
        //核实redis中是否存在验证码
        String exitCode = redisTemplate.opsForValue().get(phone);
        if(!StringUtils.isEmpty(exitCode)){
            return R.ok();
        }
        //生成验证码并发送
        String code = RandomUtil.getFourBitRandom();
        HashMap<String, Object> map = new HashMap<>();
        map.put("code", code);
        boolean sendOK = msmService.sendMsm(phone, map);

        if(sendOK){
            //将验证码存入redis,并设置过期时间
            redisTemplate.opsForValue().set(phone, code,5, TimeUnit.MINUTES);
            return R.ok();
        }
        else {
            return R.error().message("短信发送失败");
        }
    }

}
