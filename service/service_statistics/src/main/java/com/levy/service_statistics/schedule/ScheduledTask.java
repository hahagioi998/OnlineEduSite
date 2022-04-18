package com.levy.service_statistics.schedule;

import com.levy.commonutils.DateUtil;
import com.levy.service_statistics.service.StatisticsDailyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.xml.crypto.Data;
import java.util.Date;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-27 17:07
 */
@Component
public class ScheduledTask {

    @Autowired
    private StatisticsDailyService service;

    @Scheduled(cron = "0 0 1 * * ?")//每天凌晨一点执行
    public void task(){
        service.getRegisterDaily(DateUtil.formatDate(DateUtil.addDays(new Date(), -1)));
    }
}
