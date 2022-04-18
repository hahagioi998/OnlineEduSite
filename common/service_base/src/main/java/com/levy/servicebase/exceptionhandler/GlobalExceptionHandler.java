package com.levy.servicebase.exceptionhandler;

import com.levy.commonutils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @description：全局异常处理
 * @author：LevyXie
 * @create：2022-02-28 14:21
 */
@ControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    //指定根据异常确定处理机制
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public R error(Exception e){
        e.printStackTrace();
        return R.error().message("执行了全局异常处理");
    }

    //特定异常处理
    @ExceptionHandler(ArithmeticException.class)
    @ResponseBody
    public R error(ArithmeticException e){
        e.printStackTrace();
        return R.error().message("执行了特定异常处理");
    }

    //自定义异常处理
    @ExceptionHandler(GuliException.class)
    @ResponseBody
    public R error(GuliException e){
        log.error(e.getMessage());//将错误信息写入日志中的error文件中
        e.printStackTrace();
        return R.error().code(e.getCode()).message(e.getMsg());
    }
}
