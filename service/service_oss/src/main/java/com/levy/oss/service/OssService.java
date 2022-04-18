package com.levy.oss.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-11 15:58
 */
public interface OssService {

    public String uploadFileAvatar(MultipartFile file);
}
