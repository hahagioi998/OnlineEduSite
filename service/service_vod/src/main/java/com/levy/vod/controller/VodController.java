package com.levy.vod.controller;

import com.levy.commonutils.R;
import com.levy.vod.service.VodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description：视频点播前端控制器
 * @author：LevyXie
 * @create：2022-03-14 18:52
 */
@RestController
@CrossOrigin
@RequestMapping("/eduvod/video")
public class VodController {

    @Autowired
    private VodService vodService;

    @PostMapping("/upload")
    public R uploadVideo(MultipartFile file){
        String videoId = vodService.upload(file);
        return R.ok().data("videoId", videoId);
    }

    @DeleteMapping("/delete/{id}")
    public R deleteVideo(@PathVariable String id){
        vodService.deleteSourceVideo(id);
        return R.ok();
    }

    @GetMapping("/getPlayAuth/{videoId}")
    public R getPlayAuth(@PathVariable String videoId){
        String auth = vodService.getAuth(videoId);
        return R.ok().data("playAuth", auth);
    }
}
