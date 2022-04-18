package com.levy.service_statistics.controller;


import com.levy.commonutils.R;
import com.levy.service_statistics.entity.StatisticsDaily;
import com.levy.service_statistics.entity.vo.SearchVo;
import com.levy.service_statistics.service.StatisticsDailyService;
import com.levy.service_statistics.service.client.UcenterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

/**
 * <p>
 * 网站统计日数据 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@RestController
@RequestMapping("/servicesta/daily")
@CrossOrigin
public class StatisticsDailyController {

    @Autowired
    private StatisticsDailyService staService;

    @GetMapping("/saveData/{day}")
    public R registerCount(@PathVariable String day){
        staService.getRegisterDaily(day);
        return R.ok();
    }

    @PostMapping("/getChart")
    public R getDataForChart(@RequestBody SearchVo searchVo){
        HashMap<String,Object> map = staService.getChart(searchVo);
        return R.ok().data(map);
    }

}

