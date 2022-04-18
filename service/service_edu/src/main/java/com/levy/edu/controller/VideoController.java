package com.levy.edu.controller;


import com.levy.commonutils.R;
import com.levy.edu.entity.Video;
import com.levy.edu.service.VideoService;
import com.levy.edu.service.client.VodClient;
import com.levy.servicebase.exceptionhandler.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author testjava
 * @since 2022-03-12
 */
@RestController
@RequestMapping("/eduservice/video")
//@CrossOrigin
public class VideoController {
    @Autowired
    private VideoService videoService;

    @Autowired
    private VodClient vodClient;

    @PostMapping("/addVideo")
    public R addVideo(@RequestBody Video video){
        videoService.save(video);
        return R.ok();
    }
    @DeleteMapping("/deleteVideo/{id}")
    public R deleteVideo(@PathVariable String id){
        Video video = videoService.getById(id);
        String videoSourceId = video.getVideoSourceId();
        //使用nacos的注册中心，实现对另一个服务的调用
        //前置操作：两个服务均在nacos注册中心注册：配置文件+启动类注解
        //调用端的启动类添加feign注解
        //调用端service新增client接口，指定调用方法
        //以下为调用方法：
        if(!StringUtils.isEmpty(videoSourceId)){
            R r = vodClient.deleteVideo(videoSourceId);
            if(r.getCode() == 20001){
                throw new GuliException(20001, "删除云端视频失败");
            }
        }
        videoService.removeById(id);
        return R.ok();
    }
    @PostMapping("/updateVideo")
    public R updateVideo(@RequestBody Video video){
        videoService.updateById(video);
        return R.ok();
    }
    @GetMapping("/getVideo/{id}")
    public R getVideo(@PathVariable String id){
        Video video = videoService.getById(id);
        return R.ok().data("video", video);
    }

}

