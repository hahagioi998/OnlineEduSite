package com.levy.oss.controller;

import com.levy.commonutils.R;
import com.levy.oss.service.OssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @description：OSS前端控制器
 * @author：LevyXie
 * @create：2022-03-11 15:57
 */
@RestController
@RequestMapping("/eduoss/fileoss")
@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @PostMapping
    public R uploadOss(MultipartFile file){
        String avatarUrl = ossService.uploadFileAvatar(file);
        return R.ok().data("url", avatarUrl);
    }
}
