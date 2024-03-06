package com.sky.config;

import com.sky.properties.HuaWeiObsProperties;
import com.sky.utils.HuaWeiObsUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
public class HuaWeiObsConfiguration {

    @Bean
    public HuaWeiObsUtil huaWeiObsUtil(HuaWeiObsProperties huaWeiObsProperties){

        return new HuaWeiObsUtil(huaWeiObsProperties.getHuaweicloudSdkAk(),
                    huaWeiObsProperties.getHuaweicloudSdkSk(),
                    huaWeiObsProperties.getEndpoint(),
                    huaWeiObsProperties.getBucketName());

    }
}
