package com.levy.edu.service.client;

import com.levy.commonutils.R;
import org.springframework.stereotype.Component;

/**
 * @description：熔断处理机制
 * @author：LevyXie
 * @create：2022-03-16 14:58
 */
@Component//仅用于fallback处理，无实际功能
public class VodClientImpl implements VodClient{
    @Override
    public R deleteVideo(String id) {
        return R.error().message("删除程序错误！");
    }
}
