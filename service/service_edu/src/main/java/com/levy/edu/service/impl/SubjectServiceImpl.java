package com.levy.edu.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.levy.edu.entity.Subject;
import com.levy.edu.entity.excel.ExcelListener;
import com.levy.edu.entity.excel.SubjectData;
import com.levy.edu.entity.subject.OneSubject;
import com.levy.edu.entity.subject.TwoSubject;
import com.levy.edu.mapper.SubjectMapper;
import com.levy.edu.service.SubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程科目 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-11
 */
@Service
public class SubjectServiceImpl extends ServiceImpl<SubjectMapper, Subject> implements SubjectService {

    @Autowired
    private SubjectMapper mapper;

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file) {
        try {
            InputStream inputStream = file.getInputStream();
            EasyExcel.read(inputStream, SubjectData.class,new ExcelListener(this)).sheet().doRead();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Object> getSubjectData() {
        //以下为传统的数据结构嵌套写法
//        //数据结构：resultData-->parentMap-->(id,label,Map)
//        ArrayList<Object> resultData = new ArrayList<>();
//        //首先查询所有的一级分类，id = 0
//        QueryWrapper<Subject> parentWrapper = new QueryWrapper<>();
//        parentWrapper.eq("parent_id", "0");
//        List<Subject> oneSubjects = mapper.selectList(parentWrapper);
//        for (Subject oneSubject : oneSubjects) {
//            HashMap<String, Object> parentMap = new HashMap<>();
//
//            String id = oneSubject.getId();
//            String title = oneSubject.getTitle();
//            parentMap.put("id", id);
//            parentMap.put("title", title);
//
//            QueryWrapper<Subject> childWrapper = new QueryWrapper<>();
//            childWrapper.eq("parent_id", id);
//            List<Subject> twoSubjects = mapper.selectList(childWrapper);
//
//            ArrayList<Map<String, Object>> childList = new ArrayList<>();
//            for (Subject twoSubject : twoSubjects) {
//                HashMap<String, Object> childMap = new HashMap<>();
//                String cId = twoSubject.getId();
//                String cTitle = twoSubject.getTitle();
//                childMap.put("id", cId);
//                childMap.put("title", cTitle);
//                childList.add(childMap);
//            }
//            parentMap.put("children", childList);
//            resultData.add(parentMap);
//        }
//
//        return resultData;
        //以下为利用实体类封装的方式
        ArrayList<Object> resultData = new ArrayList<>();

        QueryWrapper<Subject> parentWrapper = new QueryWrapper<>();
        parentWrapper.eq("parent_id", "0");
        List<Subject> oneSubjects = mapper.selectList(parentWrapper);
        for (Subject subject : oneSubjects) {
            ArrayList<TwoSubject> children = new ArrayList<>();
            OneSubject oneSubject = new OneSubject();
            oneSubject.setId(subject.getId());
            oneSubject.setTitle(subject.getTitle());

            QueryWrapper<Subject> childWrapper = new QueryWrapper<>();
            childWrapper.eq("parent_id", subject.getId());
            List<Subject> twoSubjects = mapper.selectList(childWrapper);
            for (Subject childSubject : twoSubjects) {
                TwoSubject twoSubject = new TwoSubject();
                twoSubject.setId(childSubject.getId());
                twoSubject.setTitle(childSubject.getTitle());
                children.add(twoSubject);
            }
            oneSubject.setChildren(children);
            resultData.add(oneSubject);
        }
        return resultData;
    }

    @Override
    public List<Subject> getAllSubjectOne() {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", "0");
        List<Subject> subjects = mapper.selectList(wrapper);
        return subjects;
    }

    @Override
    public List<Subject> getAllSubjectTwo(String id) {
        QueryWrapper<Subject> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id", id);
        List<Subject> subjects = mapper.selectList(wrapper);
        return subjects;
    }
}
