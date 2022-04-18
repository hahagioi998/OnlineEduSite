package com.levy.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.levy.oss.service.OssService;
import com.levy.oss.utils.ConstantPropertiesUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;

/**
 * @description：OSS服务
 * @author：LevyXie
 * @create：2022-03-11 15:58
 */
@Service
public class OssServiceImpl implements OssService {
    @Override
    public String uploadFileAvatar(MultipartFile file) {
        String endPoint = ConstantPropertiesUtils.END_POINT;
        String keyId = ConstantPropertiesUtils.KEY_ID;
        String keySecret = ConstantPropertiesUtils.KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        String uuid = UUID.randomUUID().toString().replace("-", "");
        //获取当前日期，或可用joda-time包
        SimpleDateFormat formater = new SimpleDateFormat("yyyy/MM/dd");
        String today = formater.format(new Date());

        OSS ossClient = new OSSClientBuilder().build(endPoint,keyId,keySecret);
        try {
            InputStream inputStream = file.getInputStream();
            String filename = today + "/" + uuid + file.getOriginalFilename();
            ossClient.putObject(bucketName, filename,inputStream);
            String url = "https://" + bucketName + "." + endPoint + "/" + filename;
            return url;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            if(null != ossClient) ossClient.shutdown();
        }
    }
}
