package com.levy.edu.entity;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * @description：讲师查询的条件类
 * @author：LevyXie
 * @create：2022-02-28 11:00
 */
@Data
public class TeacherQuery {

    @ApiModelProperty(value = "讲师姓名,模糊查询")
    private String name;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "查询开始时间",example = "2022-02-02 22:22:22")
    private String begin;

    @ApiModelProperty(value = "查询结束时间",example = "2022-02-02 22:22:22")
    private String end;
}
