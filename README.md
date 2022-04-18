# 在线教育项目

## 简介

本项目为在线教育项目，是前后端分离项目。后端采用SpringCloudAlibaba微服务架构，前端采用vue框架。主要技术栈为SpringBoot 2 + MyBatisPlus  + Vue。主要实现了以下模块功能：

- 用户登录及注册模块
- 课程管理模块
- 讲师管理模块
- 基于微信支付的支付及订单模块
- 基于阿里云视频点播API的在线视频模块
- 后台权限管理模块

## 技术栈

- 前端

| 序号 | 名称                   | 类型       |
| ---- | ---------------------- | ---------- |
| 1    | Vue                    | 前端框架   |
| 2    | Nuxt                   | 前端框架   |
| 3    | Element-Admin-Template | 前端模板   |
| 4    | ElementUI              | 前端组件库 |
| 5    | Echart                 | 制图组件   |

- 后端

| 序号 | 名称                          | 类型              |
| ---- | ----------------------------- | ----------------- |
| 1    | SpringBoot                    | 后端框架          |
| 2    | SpringCloudAlibaba微服务框架  | 微服务框架        |
| 3    | MySQL 8.0                     | 数据库            |
| 4    | MyBatis Plus 3.5.1            | 持久层框架        |
| 5    | Redis                         | 缓存数据库        |
| 6    | SpringSecurity                | 权限管理框架      |
| 7    | 阿里云OSS、视频点播、短信服务 | 阿里云API         |
| 8    | 微信登录，微信支付            | 微信API           |
| 9    | EasyExcel                     | Excel处理框架     |
| 10   | Swagger 2                     | 测试及API文档框架 |

## 本地部署流程

- 项目clone至本地，修改各配置文件中的相关信息。主要包括：

  - 数据库连接信息
  - Nacos注册中心地址
  - Redis数据库地址
  - 阿里云OSS模块、视频点播模块、短信模块的地址、AccessID和AccessKey (配置中已清空密钥信息)
  - 微信登录模块的appid和appsecret

- 导入sql文件，建立数据库并导入测试数据

- 打开redis，打开nacos。

- 启动后端系统各模块（至少需要启动gateway、acl以及edu模块，方可实现最基础的首页访问功能）。

- 启动前端的后台系统`online-edu-background`及前台系统`online-edu-front`：

  ```cmd
  npm install #安装依赖的js
  npm run dev #启动
  ```

- 部署完成，访问本地3000端口即可访问前台，访问9528端口即可访问后台。

## 微服务模块说明

- api_gateway：网关
- service_acl：权限管理模块
- service_cms：轮播条管理模块
- service_edu：课程、讲师管理模块
- service_msm：短线发送模块
- service_order：用户订单模块
- service_oss：oss文件存储模块
- service_statistic：后台数据分析模块
- service_ucenter：用户注册、登录模块
- service_vod：视频点播模块

## 备注

- 本项目为Java学习项目，来源为尚硅谷发布的教程，视频教程详见：https://www.bilibili.com/video/BV1dQ4y1A75e。
- 尚未实现前台的文章模块，以及问答模块。
- 尚未实现后台的订单模块，轮播条管理模块。