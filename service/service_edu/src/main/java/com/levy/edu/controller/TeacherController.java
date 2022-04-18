package com.levy.edu.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.commonutils.R;
import com.levy.edu.entity.Teacher;
import com.levy.edu.entity.TeacherQuery;
import com.levy.edu.service.TeacherService;
import org.springframework.util.StringUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
@Api("讲师管理")//Api相关注解不加对于功能无影响，加上可在测试阶段swagger-ui处看到提示
@RestController
//@CrossOrigin
@RequestMapping("/eduservice/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("查询所有讲师")
    @GetMapping("/findAll")
    public R findAllTeacher(){
        List<Teacher> teacherList = teacherService.list(null);
        return R.ok().data("items", teacherList);
    }

    @ApiOperation("删除讲师：逻辑删除")
    @DeleteMapping("/{id}")
    public R removeTeacher(@ApiParam(name = "id",value = "讲师Id",required = true) @PathVariable("id") String id){
        boolean flag = teacherService.removeById(id);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("分页查询讲师")
    @GetMapping("/pageTeacher/{current}/{limit}")
    public R pageListTeacher(@PathVariable("current") long current,@PathVariable("limit") long limit){
        Page<Teacher> page = new Page<>(current,limit);
        Page<Teacher> teacherPage = teacherService.page(page, null);

        List<Teacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("total",total).data("rows", records);
    }

    @ApiOperation("分页条件查询讲师")
    @PostMapping("/pageTeacherCondition/{current}/{limit}")
    public R pageTeacherCondition(@PathVariable("current") long current, @PathVariable("limit") long limit,
                                  @RequestBody(required = false) TeacherQuery teacherQuery){
        Page<Teacher> page = new Page<>(current,limit);
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        String name = teacherQuery.getName();
        Integer level = teacherQuery.getLevel();
        String begin = teacherQuery.getBegin();
        String end = teacherQuery.getEnd();
        //为空的判断
        //注意此处采用的StringUtils为Spring包下的工具类
        if(!StringUtils.isEmpty(name)){
            wrapper.like("name", name);
        }
        if(!StringUtils.isEmpty(level)){
            wrapper.eq("level", level);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.ge("gmt_create",begin);
        }
        if(!StringUtils.isEmpty(begin)){
            wrapper.le("gmt_create",end);
        }
        wrapper.orderByDesc("gmt_create");
        Page<Teacher> teacherPage = teacherService.page(page, wrapper);

        List<Teacher> records = teacherPage.getRecords();
        long total = teacherPage.getTotal();
        return R.ok().data("total",total).data("rows", records);
    }

    @ApiOperation("添加讲师")
    @PostMapping("/addTeacher")
    public R addTeacher(@RequestBody Teacher teacher){
        boolean flag = teacherService.save(teacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("修改讲师")
    @PostMapping("/updateTeacher")
    public R updateTeacher(@RequestBody Teacher teacher){

        boolean flag = teacherService.updateById(teacher);
        if(flag){
            return R.ok();
        }else{
            return R.error();
        }
    }

    @ApiOperation("根据id查询讲师")
    @GetMapping("/getTeacher/{id}")
    public R getTeacher(@PathVariable("id") long id){
        Teacher teacher = teacherService.getById(id);
        return R.ok().data("teacher",teacher);
    }

}

