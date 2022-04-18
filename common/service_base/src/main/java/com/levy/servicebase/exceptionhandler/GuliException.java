package com.levy.servicebase.exceptionhandler;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @description：定义全局异常
 * @author：LevyXie
 * @create：2022-02-28 15:29
 */
@Data
@AllArgsConstructor//生成带参构造器
@NoArgsConstructor//生成无参构造器
public class GuliException extends RuntimeException{
    //异常的状态码
    private Integer code;
    //异常的信息
    private String msg;
}
