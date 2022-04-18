package com.levy.edu.service;

import com.levy.edu.entity.Subject;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-11
 */
public interface SubjectService extends IService<Subject> {

    void saveSubject(MultipartFile file);

    List<Object> getSubjectData();

    List<Subject> getAllSubjectOne();

    List<Subject> getAllSubjectTwo(String id);
}
