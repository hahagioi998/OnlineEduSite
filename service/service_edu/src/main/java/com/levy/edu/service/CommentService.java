package com.levy.edu.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.edu.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;
import com.levy.edu.entity.vo.CommentVo;

import java.util.HashMap;

/**
 * <p>
 * 评论 服务类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
public interface CommentService extends IService<Comment> {

    HashMap<String, Object> getComments(long courseId, Page<Comment> commentPage);

    boolean saveComment(CommentVo commentVo, String id);
}
