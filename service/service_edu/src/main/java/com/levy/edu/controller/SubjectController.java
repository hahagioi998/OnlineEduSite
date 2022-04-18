package com.levy.edu.controller;


import com.levy.commonutils.R;
import com.levy.edu.entity.Subject;
import com.levy.edu.service.SubjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-11
 */
@RestController
@RequestMapping("/eduservice/subject")
//@CrossOrigin
public class SubjectController {

    @Autowired
    private SubjectService subjectService;

    @PostMapping("/addSubject")
    public R addSubject(MultipartFile file){
        subjectService.saveSubject(file);
        return R.ok();
    }

    @PostMapping("/getSubjectTree")
    public R getSubjectTree(){
        List<Object> list = subjectService.getSubjectData();
        return R.ok().data("subjectTree", list);
    }

    @PostMapping("/getSubjectOnes")
    public R getSubjectones(){
        List<Subject> subjectOnes = subjectService.getAllSubjectOne();
        return R.ok().data("subjectOnes", subjectOnes);
    }

    @PostMapping("/getSubjectTwosByParentId/{id}")
    public R getSubjectTwosByParentId(@PathVariable String id){
        List<Subject> subjectTwos = subjectService.getAllSubjectTwo(id);
        return R.ok().data("subjectTwos", subjectTwos);
    }
}

