package com.levy.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.edu.entity.Teacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-02-27
 */
public interface TeacherService extends IService<Teacher> {

    Map<String,Object> getListForPage(Page<Teacher> teacherPage);
}
