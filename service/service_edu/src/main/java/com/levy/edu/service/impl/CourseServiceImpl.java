package com.levy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.levy.commonutils.R;
import com.levy.edu.entity.Chapter;
import com.levy.edu.entity.Course;
import com.levy.edu.entity.CourseDescription;
import com.levy.edu.entity.Video;
import com.levy.edu.entity.vo.CourseFrontShowVo;
import com.levy.edu.entity.vo.CourseFrontVo;
import com.levy.edu.entity.vo.CourseInfoVo;
import com.levy.edu.entity.vo.CoursePublishVo;
import com.levy.edu.mapper.ChapterMapper;
import com.levy.edu.mapper.CourseDescriptionMapper;
import com.levy.edu.mapper.CourseMapper;
import com.levy.edu.mapper.VideoMapper;
import com.levy.edu.service.CourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levy.edu.service.client.OrderClient;
import com.levy.edu.service.client.VodClient;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author testjava
 * @since 2022-03-12
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {

    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;

    @Autowired
    private ChapterMapper chapterMapper;

    @Autowired
    private VideoMapper videoMapper;

    @Autowired
    private VodClient vodClient;

    @Autowired
    private OrderClient orderClient;


    @Override
    public String saveCourseInfo(CourseInfoVo courseInfo) {
        //向课程表添加课程信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfo, course);
        int insert = baseMapper.insert(course);
        if(insert < 0){
            throw new GuliException(20001, "添加课程信息失败");
        }
        //向课程简介表添加简介信息
        String id = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfo.getDescription());
        courseDescription.setId(id);
        courseDescriptionMapper.insert(courseDescription);

        return id;
    }

    @Override
    public CourseInfoVo getCourseInfo(String id) {
        //查询两张表
        Course course = baseMapper.selectById(id);
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);

        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(course, courseInfoVo);
        courseInfoVo.setDescription(courseDescription.getDescription());
        return courseInfoVo;
    }

    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //课程表
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoVo, course);
        int update = baseMapper.updateById(course);
        if(update < 0){
            throw new GuliException(20001, "修改课程信息失败");
        }
        //课程简介表
        String id = course.getId();
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoVo.getDescription());
        courseDescription.setId(id);
        courseDescriptionMapper.updateById(courseDescription);
    }

    @Override
    public CoursePublishVo getCoursePublishVo(String id) {
        CoursePublishVo coursePublish = baseMapper.getCoursePublish(id);
        return coursePublish;
    }

    @Override
    public void deleteCourseInfo(String id) {
        //查询相关表，并先删除相关表
        QueryWrapper<Chapter> cWrapper = new QueryWrapper<>();
        cWrapper.eq("course_id", id);
        List<Chapter> chapters = chapterMapper.selectList(cWrapper);
        for (Chapter chapter : chapters) {
            QueryWrapper<Video> vWrapper = new QueryWrapper();
            vWrapper.eq("chapter_id",chapter.getId());
            //通过微服务，对每个视频进行删除
            List<Video> videos = videoMapper.selectList(vWrapper);
            for (Video video : videos) {
                String videoSourceId = video.getVideoSourceId();
                if(!StringUtils.isEmpty(videoSourceId)){
                    R r = vodClient.deleteVideo(videoSourceId);
                    if(r.getCode() == 20001){
                        throw new GuliException(20001, "删除云端视频失败");
                    }
                }
            }
            videoMapper.delete(vWrapper);
        }
        chapterMapper.delete(cWrapper);

        courseDescriptionMapper.deleteById(id);
        baseMapper.deleteById(id);
    }

    @Override
    public HashMap<String, Object> getCoursePage(Page<Course> coursePage, CourseFrontVo courseFrontVo) {
        QueryWrapper<Course> wrapper = new QueryWrapper<>();
        String parentId = courseFrontVo.getSubjectParentId();
        String childId = courseFrontVo.getSubjectId();

        String buyCount = courseFrontVo.getBuyCountSort();
        String gmtCreate = courseFrontVo.getGmtCreateSort();
        String price = courseFrontVo.getPriceSort();
        //一二级分类
        if(!StringUtils.isEmpty(parentId)){
            wrapper.eq("subject_parent_id", parentId);
        }
        if(!StringUtils.isEmpty(childId)){
            wrapper.eq("subject_id", childId);
        }
        //排序,关注度,时间,价格
        if(!StringUtils.isEmpty(buyCount)){
            wrapper.orderByDesc("buy_count");
        }
        if(!StringUtils.isEmpty(gmtCreate)){
            wrapper.orderByDesc("gmt_create");
        }
        if(!StringUtils.isEmpty(price)){
            wrapper.orderByDesc("price");
        }
        Page<Course> pageInfo = baseMapper.selectPage(coursePage, wrapper);

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
    public CourseFrontShowVo getCourseShowInfo(String id) {
        CourseFrontShowVo result = baseMapper.getCourseShow(id);
        return result;
    }

    @Override
    public boolean getPayStatus(String id, String memberId) {
        return orderClient.getPayStatus(id, memberId);
    }


}
