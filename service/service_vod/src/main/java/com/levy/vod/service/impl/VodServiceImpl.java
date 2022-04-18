package com.levy.vod.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.vod.model.v20170321.DeleteVideoRequest;
import com.aliyuncs.vod.model.v20170321.DeleteVideoResponse;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.levy.servicebase.exceptionhandler.GuliException;
import com.levy.vod.service.VodService;
import com.levy.vod.utils.ConstantPropertiesUtils;
import com.levy.vod.utils.VodUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;

/**
 * @description：视频点播Service
 * @author：LevyXie
 * @create：2022-03-14 19:01
 */
@Service
public class VodServiceImpl implements VodService {

    @Override
    public String upload(MultipartFile file) {
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        //初始化文件上传的相关参数
        String fileName = file.getOriginalFilename();
        String title = fileName.substring(0,fileName.lastIndexOf("."));
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
        }

        UploadStreamRequest request = new UploadStreamRequest(keyId, keySecret, title, fileName, inputStream);

        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);
        String videoId = null;
        if (response.isSuccess()) {
            videoId = response.getVideoId();
        } else { //如果设置回调URL无效，不影响视频上传，可以返回VideoId同时会返回错误码。其他情况上传失败时，VideoId为空，此时需要根据返回错误码分析具体错误原因
            throw new GuliException(20001, response.getMessage());
        }
        return videoId;
    }

    @Override
    public void deleteSourceVideo(String id) {
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        try {
            DefaultAcsClient client = VodUtils.initVodClient(keyId, keySecret);

            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(id);

            DeleteVideoResponse response = client.getAcsResponse(request);

        } catch (ClientException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String getAuth(String videoId) {
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        DefaultAcsClient client = null;
        GetVideoPlayAuthResponse response = new GetVideoPlayAuthResponse();
        try {
            client = VodUtils.initVodClient(keyId, keySecret);

            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            request.setVideoId("841a1ead7f51462d9c588fe72baa0810");

            response = client.getAcsResponse(request);
            return response.getPlayAuth();

        } catch (ClientException e) {
            throw new GuliException(20001, "视频加载错误");
        }
    }
}
