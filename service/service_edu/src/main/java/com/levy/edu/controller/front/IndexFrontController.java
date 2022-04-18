package com.levy.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.levy.commonutils.R;
import com.levy.edu.entity.Course;
import com.levy.edu.entity.Teacher;
import com.levy.edu.service.CourseService;
import com.levy.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @description：首页相关的课程、讲师查询
 * @author：LevyXie
 * @create：2022-03-19 22:38
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/indexfront")
public class IndexFrontController {

    @Autowired
    private CourseService courseService;

    @Autowired
    private TeacherService teacherService;

    @Cacheable(value = "indexData",key = "'teacher&course'")
    @GetMapping("getIndexCourseTeacher")
    public R getIndexCourse(){
        //首页课程
        QueryWrapper<Course> cWrapper = new QueryWrapper<>();
        cWrapper.orderByDesc("id");
        cWrapper.last("limit 8");
        List<Course> courseList = courseService.list(cWrapper);
        //首页讲师
        QueryWrapper<Teacher> tWrapper = new QueryWrapper<>();
        tWrapper.orderByDesc("id");
        tWrapper.last("limit 4");
        List<Teacher> teacherList = teacherService.list(tWrapper);
        return R.ok().data("indexCourse",courseList).data("indexTeacher",teacherList);
    }
}
