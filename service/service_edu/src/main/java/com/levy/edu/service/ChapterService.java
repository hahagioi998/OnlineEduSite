package com.levy.edu.service;

import com.levy.edu.entity.Chapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levy.edu.entity.chapter.ChapterVo;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-12
 */
public interface ChapterService extends IService<Chapter> {

    List<ChapterVo> getChapterTree(String courseId);

    Boolean deleteWhenEmpty(String id);
}
