package com.levy.commonutils;

import lombok.Data;

import java.util.HashMap;
import java.util.Map;

/**
 * @description：统一返回结果
 * @author：LevyXie
 * @create：2022-02-28 10:02
 */
@Data
public class R {

    private Boolean success;
    private String message;
    private Integer code;
    private Map<String,Object> data = new HashMap<String,Object>();

    //私有构造器，不允许外部调用，只能通过方法产生R对象
    private R(){}

    //成功和失败情况下的r对象
    public static R ok(){
        R r = new R();
        r.setSuccess(true);
        r.setCode(ResultCode.SUCCESS);
        r.setMessage("成功");
        return r;
    }

    public static R error(){
        R r = new R();
        r.setSuccess(false);
        r.setCode(ResultCode.ERROR);
        r.setMessage("失败");
        return r;
    }

    //r对象的各属性的赋值方法
    public R success(Boolean success){
        this.setSuccess(success);
        return this;
    }

    public R message(String message){
        this.setMessage(message);
        return this;
    }

    public R code(Integer code){
        this.setCode(code);
        return this;
    }

    public R data(String key, Object value){
        this.data.put(key, value);
        return this;
    }

    public R data(Map<String, Object> map){
        this.setData(map);
        return this;
    }
}
