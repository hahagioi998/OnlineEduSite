package com.levy.edu.controller.front;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.commonutils.R;
import com.levy.edu.entity.Course;
import com.levy.edu.entity.Teacher;
import com.levy.edu.service.CourseService;
import com.levy.edu.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * @description：前台讲师页面的Controller
 * @author：LevyXie
 * @create：2022-03-22 14:21
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacherfront")
public class TeacherFrontController {

    @Autowired
    private TeacherService teacherService;

    @Autowired
    private CourseService courseService;

    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageTeacher(@PathVariable long current,
                         @PathVariable long limit){
        Page<Teacher> teacherPage = new Page<>(current,limit);

        Map<String,Object> map  = teacherService.getListForPage(teacherPage);

        return R.ok().data(map);
    }

    @GetMapping("/getFrontTeacher/{id}")
    public R getTeacher(@PathVariable long id){
        Teacher teacher = teacherService.getById(id);
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        wrapper.eq("teacher_id", id);
        List<Course> courseList = courseService.list(wrapper);

        return R.ok().data("teacher", teacher).data("courseList", courseList);
    }

}
