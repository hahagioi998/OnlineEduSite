package com.levy.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.edu.entity.Course;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levy.edu.entity.vo.CourseFrontShowVo;
import com.levy.edu.entity.vo.CourseFrontVo;
import com.levy.edu.entity.vo.CourseInfoVo;
import com.levy.edu.entity.vo.CoursePublishVo;

import java.util.HashMap;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-12
 */
public interface CourseService extends IService<Course> {

    String saveCourseInfo(CourseInfoVo courseInfo);

    CourseInfoVo getCourseInfo(String id);

    void updateCourseInfo(CourseInfoVo courseInfoVo);

    CoursePublishVo getCoursePublishVo(String id);

    void deleteCourseInfo(String id);

    HashMap<String, Object> getCoursePage(Page<Course> coursePage, CourseFrontVo courseFrontVo);

    CourseFrontShowVo getCourseShowInfo(String id);

    boolean getPayStatus(String id, String memberId);
}
