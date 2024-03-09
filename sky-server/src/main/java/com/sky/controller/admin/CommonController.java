package com.sky.controller.admin;

import com.sky.result.Result;
import com.sky.utils.HuaWeiObsUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/admin/common")
@Api(tags = "通用业务")
@Slf4j
public class CommonController {

    @Autowired
    private HuaWeiObsUtil huaWeiObsUtil;

    @PostMapping("/upload")
    @ApiOperation("图片上传")
    public Result<String> upload(MultipartFile file){

        String filesPath;

        try {

            //获取文件后缀名
            String originalFilename = file.getOriginalFilename();
            String extension = originalFilename.substring(originalFilename.lastIndexOf("."));
            //生成UUID作为文件名
            String objectName = UUID.randomUUID().toString() + extension;

            File upFile = new File("F:\\upFail\\" + objectName);

            file.transferTo(upFile);

            filesPath = huaWeiObsUtil.uploadFiles(upFile);

            upFile.delete();

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return Result.success(filesPath);

    }

}
