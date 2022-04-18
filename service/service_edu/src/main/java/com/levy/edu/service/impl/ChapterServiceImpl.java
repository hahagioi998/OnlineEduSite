package com.levy.edu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.levy.edu.entity.Chapter;
import com.levy.edu.entity.Video;
import com.levy.edu.entity.chapter.ChapterVo;
import com.levy.edu.entity.chapter.VideoVo;
import com.levy.edu.mapper.ChapterMapper;
import com.levy.edu.mapper.VideoMapper;
import com.levy.edu.service.ChapterService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private ChapterMapper cMapper;
    @Autowired
    private VideoMapper vMapper;

    @Override
    public List<ChapterVo> getChapterTree(String courseId) {
        ArrayList<ChapterVo> resultData = new ArrayList<>();

        QueryWrapper<Chapter> parentWrapper = new QueryWrapper<>();
        parentWrapper.eq("course_id", courseId);
        List<Chapter> chapters = cMapper.selectList(parentWrapper);
        for (Chapter chapter : chapters) {
            ArrayList<VideoVo> children = new ArrayList<>();
            ChapterVo chapterVo = new ChapterVo();
            chapterVo.setId(chapter.getId());
            chapterVo.setTitle(chapter.getTitle());

            QueryWrapper<Video> childWrapper = new QueryWrapper<>();
            childWrapper.eq("chapter_id", chapterVo.getId());
            List<Video> videos= vMapper.selectList(childWrapper);
            for (Video video : videos) {
                VideoVo videoVo = new VideoVo();
                videoVo.setId(video.getId());
                videoVo.setTitle(video.getTitle());
                videoVo.setVideoSourceId(video.getVideoSourceId());
                children.add(videoVo);
            }
            chapterVo.setChildren(children);
            resultData.add(chapterVo);
        }
        return resultData;
    }

    @Override
    public Boolean deleteWhenEmpty(String id) {
        //仅当无子项时删除
        QueryWrapper<Video> videoWrapper = new QueryWrapper<>();
        videoWrapper.eq("chapter_id", id);

        Long count = vMapper.selectCount(videoWrapper);
        if(count > 0){
            throw new GuliException(20001, "无法删除");
        }else{
            int result = cMapper.deleteById(id);
            return result > 0;
        }
    }
}
