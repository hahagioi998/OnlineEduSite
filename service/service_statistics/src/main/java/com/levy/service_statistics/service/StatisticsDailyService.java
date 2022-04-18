package com.levy.service_statistics.service;

import com.levy.commonutils.R;
import com.levy.service_statistics.entity.StatisticsDaily;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levy.service_statistics.entity.vo.SearchVo;

import java.util.HashMap;

/**
 * <p>
 * 网站统计日数据 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
public interface StatisticsDailyService extends IService<StatisticsDaily> {

    void getRegisterDaily(String day);

    HashMap<String, Object> getChart(SearchVo searchVo);
}
