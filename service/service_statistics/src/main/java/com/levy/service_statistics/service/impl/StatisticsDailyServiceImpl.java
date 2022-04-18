package com.levy.service_statistics.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.levy.commonutils.RandomUtil;
import com.levy.service_statistics.entity.StatisticsDaily;
import com.levy.service_statistics.entity.vo.SearchVo;
import com.levy.service_statistics.mapper.StatisticsDailyMapper;
import com.levy.service_statistics.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levy.service_statistics.service.client.UcenterClient;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 网站统计日数据 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-27
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {
    @Autowired
    private UcenterClient ucenterClient;

    @Override
    public void getRegisterDaily(String day) {
        //首先检查表中是否已有数据，已有就删除再添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated", day);
        baseMapper.delete(wrapper);
        //添加
        Integer registerNum = (Integer) ucenterClient.getRegisterDaily(day).getData().get("num");
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(registerNum);
        statisticsDaily.setDateCalculated(day);
        //以下为模拟数据
        Integer loginNum = RandomUtils.nextInt(100,300);
        Integer videoViewNum = RandomUtils.nextInt(100,300);
        Integer courseNum = RandomUtils.nextInt(1,10);

        statisticsDaily.setCourseNum(courseNum);
        statisticsDaily.setVideoViewNum(videoViewNum);
        statisticsDaily.setLoginNum(loginNum);

        baseMapper.insert(statisticsDaily);
    }

    @Override
    public HashMap<String, Object> getChart(SearchVo searchVo) {
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated", searchVo.startDate, searchVo.endDate);
        wrapper.select("date_calculated",searchVo.type);
        List<StatisticsDaily> RawData = baseMapper.selectList(wrapper);
        List<String> xData = new ArrayList<>();
        List<Integer> yData = new ArrayList<>();


        for (StatisticsDaily data : RawData) {
            xData.add(data.getDateCalculated());
            switch (searchVo.type){
                case "register_num":
                    yData.add(data.getRegisterNum());
                    break;
                case "login_num":
                    yData.add(data.getLoginNum());
                    break;
                case "video_view_num":
                    yData.add(data.getVideoViewNum());
                    break;
                case "course_num":
                    yData.add(data.getCourseNum());
                    break;
            }
        }
        HashMap<String, Object> map = new HashMap<>();
        map.put("xData", xData);
        map.put("yData", yData);
        return map;
    }
}
