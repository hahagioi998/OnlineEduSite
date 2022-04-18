package com.levy.vod.utils;

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
    @Value("${aliyun.oss.file.keyid}")
    private String keyId;

    @Value("${aliyun.oss.file.keysecret}")
    private String keySerret;

    public static String KEY_ID;
    public static String KEY_SECRET;

//    项目启动后自动执行以下方法，取到配置文件中的常量值
    @Override
    public void afterPropertiesSet() throws Exception {
        KEY_ID = keyId;
        KEY_SECRET = keySerret;
    }
}
