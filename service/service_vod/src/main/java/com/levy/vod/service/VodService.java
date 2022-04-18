package com.levy.vod.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @description：
 * @author：LevyXie
 * @create：2022-03-14 19:01
 */
public interface VodService {
    String upload(MultipartFile file);

    void deleteSourceVideo(String id);

    String getAuth(String videoId);
}
