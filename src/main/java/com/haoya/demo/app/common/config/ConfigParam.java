package com.haoya.demo.app.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigParam {

    public String getUploadPath() {
        return uploadPath;
    }

    @Value("${system.upload-path}")
    private String uploadPath;
}
