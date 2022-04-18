package com.levy.edu.entity.vo;

import lombok.Data;

/**
 * @description：用于回传给前端发布页面的course封装数据
 * @author：LevyXie
 * @create：2022-03-13 19:24
 */
@Data
public class CoursePublishVo {

    private static final long serialVersionUID = 1L;

    private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;

}
