package com.levy.edu.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.commonutils.JwtUtils;
import com.levy.commonutils.R;
import com.levy.edu.entity.Course;
import com.levy.edu.entity.chapter.ChapterVo;
import com.levy.edu.entity.vo.CourseFrontShowVo;
import com.levy.edu.entity.vo.CourseFrontVo;
import com.levy.edu.service.ChapterService;
import com.levy.edu.service.CourseService;
import com.levy.edu.service.client.OrderClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;

/**
 * @description：前台课程页面的Controller
 * @author：LevyXie
 * @create：2022-03-22 16:29
 */
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/coursefront")
public class CourseFrontController {
    @Autowired
    private CourseService courseService;

    @Autowired
    private ChapterService chapterService;

    @PostMapping("/getCoursePage/{current}/{limit}")
    public R getCoursePage(@PathVariable long current,
                           @PathVariable long limit,
                           @RequestBody(required = false) CourseFrontVo courseFrontVo){
        Page<Course> coursePage = new Page<>(current,limit);
        HashMap<String,Object> map = courseService.getCoursePage(coursePage,courseFrontVo);
        return R.ok().data(map);
    }

    @GetMapping("/showCourseInfo/{id}")
    public R showCourseInfo(@PathVariable String id,HttpServletRequest request){
        CourseFrontShowVo info = courseService.getCourseShowInfo(id);
        List<ChapterVo> chapterTree = chapterService.getChapterTree(id);
        String memberId = JwtUtils.getMemberIdByJwtToken(request);
        boolean payStatus;
        if(StringUtils.isEmpty(memberId)){
            payStatus = false;
        }else {
            payStatus = courseService.getPayStatus(id,memberId);
        }
        return R.ok().data("courseInfo", info).data("chapterTree", chapterTree).data("payStatus",payStatus);
    }

}
