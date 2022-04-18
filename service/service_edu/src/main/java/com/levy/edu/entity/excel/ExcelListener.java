package com.levy.edu.entity.excel;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.levy.edu.entity.Subject;
import com.levy.edu.service.SubjectService;
import com.levy.servicebase.exceptionhandler.GuliException;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-11 22:18
 */
public class ExcelListener extends AnalysisEventListener<SubjectData> {

    private SubjectService subjectService;

    public ExcelListener() {
    }

    public ExcelListener(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @Override
    public void invoke(SubjectData subjectData, AnalysisContext analysisContext) {
        if(subjectData == null){
            throw new GuliException(20001,"文件数据为空");
        }
        //一级分类判断是否已经存在
        Subject subjectOne = alreadyExistOne(subjectData.getOneSubjectName(), subjectService);
        if(subjectOne == null){
            subjectOne = new Subject();
            subjectOne.setTitle(subjectData.getOneSubjectName());
            subjectOne.setParentId("0");
            subjectService.save(subjectOne);
        }
        //二级分类判断是否已经存在
        Subject subjectTwo = alreadyExistTwo(subjectData.getTwoSubjectName(), subjectService, subjectOne.getId());
        if(subjectTwo == null){
            subjectTwo = new Subject();
            subjectTwo.setTitle(subjectData.getTwoSubjectName());
            subjectTwo.setParentId(subjectOne.getId());
            subjectService.save(subjectTwo);
        }
    }

    //针对一级分类判断是否已经存在的方法
    private Subject alreadyExistOne(String name,SubjectService subjectService){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id", "0" );
        Subject one = subjectService.getOne(wrapper);
        return one;
    }
    //针对二级分类判断是否已经存在的方法
    private Subject alreadyExistTwo(String name,SubjectService subjectService,String pid){
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("title",name);
        wrapper.eq("parent_id", pid );
        Subject two = subjectService.getOne(wrapper);
        return two;
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
