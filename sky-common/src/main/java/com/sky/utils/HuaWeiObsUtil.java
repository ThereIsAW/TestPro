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
    private static String ak = System.getenv("HUAWEICLOUD_SDK_AK");
    private static String sk = System.getenv("HUAWEICLOUD_SDK_SK");
    private static String endpoint = "http://your-endpoint";

    private static ObsClient obsClient;

    private static String bucketName = "you-bucket-name";

    private static String remotePrefix = "remote_prefix";
    private static String localFolder = "you/local/path";
    private static List<String> failedList = new ArrayList<>();

    private static int prefixLength = localFolder.length();

    public static void main(String[] args) throws IOException {
        obsClient = new ObsClient(ak, sk, endpoint);
        File needUploadFolder = new File(localFolder);
        uploadFiles(needUploadFolder);
        failedList.forEach(item -> System.out.println("Failed to upload " + item + ", please try again"));
    }

    private static void uploadFiles(File file) throws IOException {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                uploadFiles(f);
                continue;
            }
            String objectKey = remotePrefix + f.getCanonicalPath().substring(prefixLength).replace(File.separator, "/");

            System.out.println("Start to upload " + f.getCanonicalPath() + " to OBS, using objectKey: " + objectKey);
            UploadFileRequest request = new UploadFileRequest(bucketName, objectKey);
            request.setUploadFile(f.getCanonicalPath());
            request.setEncodingType("url");
            try {
                obsClient.uploadFile(request);
            } catch (ObsException e) {
                failedList.add(f.getCanonicalPath());
            }

        }
    }
}

public class UploadFolder {
    private static String ak = "Your Access Key";
    private static String sk = "Your Secret Access Key";
    private static String endpoint = "http://your-endpoint";

    private static ObsClient obsClient;

    private static String bucketName = "you-bucket-name";

    private static String remotePrefix = "remote_prefix";
    private static String localFolder = "you/local/path";
    private static List<String> failedList = new ArrayList<>();

    private static int prefixLength = localFolder.length();

    public static void main(String[] args) throws IOException {
        obsClient = new ObsClient(ak, sk, endpoint);
        File needUploadFolder = new File(localFolder);
        uploadFiles(needUploadFolder);
        failedList.forEach(item -> System.out.println("Failed to upload " + item + ", please try again"));
    }

    private static void uploadFiles(File file) throws IOException {
        File[] files = file.listFiles();
        for (File f : files) {
            if (f.isDirectory()) {
                uploadFiles(f);
                continue;
            }
            String objectKey = remotePrefix + f.getCanonicalPath().substring(prefixLength).replace(File.separator, "/");

            System.out.println("Start to upload " + f.getCanonicalPath() + " to OBS, using objectKey: " + objectKey);
            UploadFileRequest request = new UploadFileRequest(bucketName, objectKey);
            request.setUploadFile(f.getCanonicalPath());
            request.setEncodingType("url");
            try {
                obsClient.uploadFile(request);
            } catch (ObsException e) {
                failedList.add(f.getCanonicalPath());
            }

        }
    }
}



