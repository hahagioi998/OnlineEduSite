package com.levy.edu.controller;


import com.levy.commonutils.R;
import com.levy.edu.entity.Chapter;
import com.levy.edu.entity.chapter.ChapterVo;
import com.levy.edu.service.ChapterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 课程 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/chapter")
//@CrossOrigin
public class ChapterController {
    @Autowired
    private ChapterService chapterService;

    @GetMapping("/getChapterTree/{courseId}")
    public R getChapterTree(@PathVariable String courseId){
        List<ChapterVo> list = chapterService.getChapterTree(courseId);
        return R.ok().data("chapterTree",list);
    }

    @PostMapping("/addChapter")
    public R addChapter(@RequestBody Chapter chapter){
        chapterService.save(chapter);
        return R.ok();
    }

    @GetMapping("/getChapter/{id}")
    public R getChapter(@PathVariable String id){
        Chapter chapter = chapterService.getById(id);
        return R.ok().data("chapter", chapter);
    }

    @PostMapping("/updateChapter")
    public R updateChapter(@RequestBody Chapter chapter){
        chapterService.updateById(chapter);
        return R.ok();
    }

    @DeleteMapping("/deleteChapter/{id}")
    public R deleteChapter(@PathVariable String id){
        Boolean flag = chapterService.deleteWhenEmpty(id);
        if(flag){
            return R.ok();
        }else {
            return R.error();
        }
    }

}

