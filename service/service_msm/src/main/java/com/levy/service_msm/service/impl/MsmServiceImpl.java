package com.levy.service_msm.service.impl;

import com.aliyun.dysmsapi20170525.Client;
import com.aliyun.dysmsapi20170525.models.SendSmsRequest;
import com.aliyun.teaopenapi.models.Config;
import com.levy.service_msm.service.MsmService;
import com.levy.service_msm.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;

/**
 * @description：短信服务的Service模块
 * @author：LevyXie
 * @create：2022-03-20 15:15
 */
@Service
public class MsmServiceImpl implements MsmService {

    @Override
    public boolean sendMsm(String phone, HashMap<String, Object> map) {
        //判空，则直接返回
        if(StringUtils.isEmpty(phone)){
            return false;
        }
        //从工具类读取id和密钥
        String id = ConstantPropertiesUtils.KEY_ID;
        String secret = ConstantPropertiesUtils.KEY_SECRET;

        //以下为aliyun的官方api代码(测试专用的api,需登记测试手机号,实际生产需申请签名，模板)
        Config config = new Config()
                // 您的AccessKey ID
                .setAccessKeyId(id)
                // 您的AccessKey Secret
                .setAccessKeySecret(secret);
        // 访问的域名
        config.endpoint = "dysmsapi.aliyuncs.com";
        try {
            Client client = new Client(config);
            SendSmsRequest sendSmsRequest = new SendSmsRequest()
                    .setSignName("阿里云短信测试")
                    .setTemplateCode("SMS_154950909")
                    .setPhoneNumbers(phone)
                    .setTemplateParam("{\"code\":\"" + map.get("code") + "\"}");
            client.sendSms(sendSmsRequest);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
