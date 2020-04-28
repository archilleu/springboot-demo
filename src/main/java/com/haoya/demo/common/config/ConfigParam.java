package com.haoya.demo.common.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConfigParam {

    public String getBackgroundPath() {
        return backgroundPath;
    }

    public String getUploadPath() {
        return uploadPath;
    }

    public String getLogoPath() {
        return logoPath;
    }

    @Value("${system.logo-path}")
    private String logoPath;

    @Value("${system.background-path}")
    private String backgroundPath;

    @Value("${system.upload-path}")
    private String uploadPath;
}
