package com.levy.service_statistics.entity.vo;

import lombok.Data;

/**
 * @description：查询数据的Vo
 * @author：LevyXie
 * @create：2022-03-27 18:32
 */
@Data
public class SearchVo {
    public String type;
    public String startDate;
    public String endDate;
}
