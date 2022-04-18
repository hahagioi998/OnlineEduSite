package com.levy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.edu.entity.Teacher;
import com.levy.edu.mapper.TeacherMapper;
import com.levy.edu.service.TeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 讲师 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
@Service
public class TeacherServiceImpl extends ServiceImpl<TeacherMapper, Teacher> implements TeacherService {

    @Override
    public Map<String,Object> getListForPage(Page<Teacher> teacherPage) {
        //对查询到的讲师按id降序排列
        QueryWrapper<Teacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");

        baseMapper.selectPage(teacherPage, wrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", teacherPage.getTotal());//总记录数
        map.put("records", teacherPage.getRecords());//所有数据
        map.put("current", teacherPage.getCurrent());//当前页
        map.put("size", teacherPage.getSize());//每页记录数
        map.put("pages", teacherPage.getPages());//总页数
        map.put("hasNext", teacherPage.hasNext());
        map.put("hasPrevious", teacherPage.hasPrevious());

        return map;
    }
}
