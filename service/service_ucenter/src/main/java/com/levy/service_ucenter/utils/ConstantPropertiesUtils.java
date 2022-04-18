package com.levy.service_ucenter.utils;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @description：读取配置文件
 * @author：LevyXie
 * @create：2022-03-11 15:47
 */
@Component
public class ConstantPropertiesUtils implements InitializingBean {

    //通过value注解，读取配置文件内容
    @Value("${wx.open.app_id}")
    private String appId;

    @Value("${wx.open.app_secret}")
    private String appSercet;

    @Value("${wx.open.redirect_url}")
    private String redirectUrl;

    public static String APP_ID;
    public static String APP_SECRET;
    public static String REDIRECT_URL;

//    项目启动后自动执行以下方法，取到配置文件中的常量值
    @Override
    public void afterPropertiesSet() throws Exception {
        APP_ID = appId;
        APP_SECRET = appSercet;
        REDIRECT_URL = redirectUrl;
    }
}
