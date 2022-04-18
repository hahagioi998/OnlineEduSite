package com.levy.edu.mapper;

import com.levy.edu.entity.Course;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.levy.edu.entity.vo.CourseFrontShowVo;
import com.levy.edu.entity.vo.CoursePublishVo;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author testjava
 * @since 2022-03-12
 */
public interface CourseMapper extends BaseMapper<Course> {

    CoursePublishVo getCoursePublish(String id);

    CourseFrontShowVo getCourseShow(String id);
}
