package com.sky.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "sky.huaweiobs")
@Data
public class HuaWeiObsProperties {

    private String huaweicloudSdkAk;
    private String huaweicloudSdkSk;
    private String endpoint;
    private String bucketNamel;
    private String remotePrefix;

}
