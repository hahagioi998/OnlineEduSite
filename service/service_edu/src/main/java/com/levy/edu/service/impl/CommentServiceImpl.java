package com.levy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.edu.entity.Comment;
import com.levy.edu.entity.Course;
import com.levy.edu.entity.UcenterMember;
import com.levy.edu.entity.vo.CommentVo;
import com.levy.edu.mapper.CommentMapper;
import com.levy.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levy.edu.service.CourseService;
import com.levy.edu.service.client.UcenterClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-24
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UcenterClient ucenterClient;

    @Autowired
    private CourseService courseService;

    @Override
    public HashMap<String, Object> getComments(long courseId, Page<Comment> commentPage) {
        QueryWrapper<Comment> wrapper = new QueryWrapper<>();
        wrapper.eq("course_id", courseId);
        wrapper.orderByDesc("gmt_modified");
        Page<Comment> pageInfo = baseMapper.selectPage(commentPage, wrapper);

        HashMap<String, Object> map = new HashMap<>();
        map.put("total", pageInfo.getTotal());//总记录数
        map.put("records", pageInfo.getRecords());//所有数据
        map.put("current", pageInfo.getCurrent());//当前页
        map.put("size", pageInfo.getSize());//每页记录数
        map.put("pages", pageInfo.getPages());//总页数
        map.put("hasNext", pageInfo.hasNext());
        map.put("hasPrevious", pageInfo.hasPrevious());
        return map;
    }

    @Override
    public boolean saveComment(CommentVo commentVo, String id) {
        Comment comment = new Comment();
        UcenterMember member = ucenterClient.getMemberById(id);
        System.out.println(member);
        if(null == member){
            return false;
        }
        Course course = courseService.getById(commentVo.getCourseId());

        comment.setCourseId(commentVo.getCourseId());
        comment.setContent(commentVo.getContent());
        comment.setTeacherId(course.getTeacherId());
        comment.setMemberId(member.getId());
        comment.setNickname(member.getNickname());
        comment.setAvatar(member.getAvatar());

        baseMapper.insert(comment);
        return true;
    }

}
