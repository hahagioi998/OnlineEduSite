package com.levy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.commonutils.JwtUtils;
import com.levy.commonutils.R;
import com.levy.commonutils.orderVo.CourseOrderVo;
import com.levy.edu.entity.Course;
import com.levy.edu.entity.CourseQuery;
import com.levy.edu.entity.Teacher;
import com.levy.edu.entity.vo.CourseInfoVo;
import com.levy.edu.entity.vo.CoursePublishVo;
import com.levy.edu.service.CourseService;
import com.levy.edu.service.TeacherService;
import com.levy.edu.service.client.OrderClient;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/course")
//@CrossOrigin
public class CourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private TeacherService teacherService;
    @Autowired
    private OrderClient orderClient;

    @PostMapping("/addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfo){
        String id = courseService.saveCourseInfo(courseInfo);
        return R.ok().data("id", id);
    }

    @GetMapping("/getCourseInfo/{id}")
    public R getCourseInfo(@PathVariable String id){
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(id);
        return R.ok().data("courseInfoVo", courseInfoVo);
    }

    @PostMapping("/updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        courseService.updateCourseInfo(courseInfoVo);
        return R.ok();
    }
    @GetMapping("/getBeforePublish/{id}")
    public R getBeforePublish(@PathVariable String id){
        CoursePublishVo coursePublishVo = courseService.getCoursePublishVo(id);
        return R.ok().data("course", coursePublishVo);
    }
    @PostMapping("/finalPublish/{id}")
    public R finalPublish(@PathVariable String id){
        Course course = new Course();
        course.setId(id);
        course.setStatus("Normal");
        courseService.updateById(course);
        return R.ok();
    }

    @GetMapping("/getCourseList")
    public R getCourseList(){
        List<Course> allCourse = courseService.list();
        return R.ok().data("allCourse",allCourse);
    }
    @PostMapping("/pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable("current") long current, @PathVariable("limit") long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery){
        Page<Course> page = new Page<>(current,limit);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        System.out.println(courseQuery);
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();
        String begin = courseQuery.getBegin();
        String end = courseQuery.getEnd();
        //注意此处采用的StringUtils为Spring包下的工具类
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title", title);
        }
        if(!StringUtils.isEmpty(status)){
            wrapper.eq("status", status);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");
        Page<Course> coursePage = courseService.page(page, wrapper);

        List<Course> records = coursePage.getRecords();
        long total = coursePage.getTotal();
        return R.ok().data("total",total).data("rows", records);
    }

    @DeleteMapping("/deleteCourseById/{id}")
    public R deleteCourseById(@PathVariable String id){
        courseService.deleteCourseInfo(id);
        return R.ok();
    }

    @GetMapping("/getCourseOrder/{id}")
    public CourseOrderVo getCourseOrder(@PathVariable String id){
        CourseOrderVo courseOrderVo = new CourseOrderVo();
        Course course = courseService.getById(id);
        Teacher teacher = teacherService.getById(course.getTeacherId());

        BeanUtils.copyProperties(course, courseOrderVo);
        courseOrderVo.setTeacherName(teacher.getName());
        return courseOrderVo;
    }
}

