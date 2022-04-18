package com.levy.edu.entity;

import lombok.Data;

/**
 * @description：查询课程的参数
 * @author：LevyXie
 * @create：2022-03-14 13:17
 */
@Data
public class CourseQuery {

    private String title;

    private String status;

    private String begin;

    private String end;

}
