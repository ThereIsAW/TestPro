package com.sky.utils;

import com.obs.services.ObsClient;
import com.obs.services.exception.ObsException;
import com.obs.services.model.UploadFileRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@Slf4j
public class HuaWeiObsUtil {
    // 认证用的ak和sk直接写到代码中有很大的安全风险，建议在配置文件或者环境变量中密文存放，使用时解密，确保安全；
    // 本示例以ak和sk保存在环境变量中来实现身份验证为例，运行本示例前请先在本地环境中设置环境变量HUAWEICLOUD_SDK_AK和HUAWEICLOUD_SDK_SK。
    private  String HUAWEICLOUD_SDK_AK;
    private  String HUAWEICLOUD_SDK_SK;
    private  String endpoint;
    private  String bucketName;

    public String uploadFiles(File file) throws IOException {

        List<String> failedList = new ArrayList<>();

        ObsClient obsClient = new ObsClient(HUAWEICLOUD_SDK_AK, HUAWEICLOUD_SDK_SK, endpoint);

        StringBuilder stringBuilder = new StringBuilder("https://");
        //获取文件后缀名
        String name = file.getName();
        String remotePrefix = name.substring(name.lastIndexOf("."));

        int pathLength = file.getCanonicalPath().lastIndexOf("\\") + 1 ;

        String objectKey = file.getCanonicalPath().substring(pathLength);

        System.out.println("Start to upload " + file.getCanonicalPath() + " to OBS, using objectKey: " + objectKey);
        UploadFileRequest request = new UploadFileRequest(bucketName, objectKey);
        request.setUploadFile(file.getCanonicalPath());
        request.setEncodingType("url");
        try {
            obsClient.uploadFile(request);
        } catch (ObsException e) {
            failedList.add(file.getCanonicalPath());
        }


        failedList.forEach(item -> System.out.println("Failed to upload " + item + ", please try again"));

        stringBuilder.append(bucketName).append(".").append(endpoint).append("/").append(file.getName());
        log.info("文件已上传到：{}",stringBuilder.toString());

        return stringBuilder.toString();
    }
}





