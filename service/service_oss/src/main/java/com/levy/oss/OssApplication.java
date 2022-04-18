package com.levy.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-11 15:35
 */
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)//避免默认加载数据库
@ComponentScan(basePackages = {"com.levy"})
@EnableDiscoveryClient
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class, args);
    }
}
