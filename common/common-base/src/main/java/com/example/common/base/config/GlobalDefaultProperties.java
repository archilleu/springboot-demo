package com.example.common.base.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * dispose配置文件
 *
 * @author cjy
 */

@Data
@Configuration
@ConfigurationProperties(prefix = GlobalDefaultProperties.PREFIX)
public class GlobalDefaultProperties {

    public static final String PREFIX = "dispose";

    /**
     * 统一返回过滤包
     */
    @Value("${advice-package:[]}")
    private List<String> advicePackage;

    /**
     * 统一返回过滤类
     */
    @Value("${advice-class:[]}")
    private List<String> adviceClass;
}
