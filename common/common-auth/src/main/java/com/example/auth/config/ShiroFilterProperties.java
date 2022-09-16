package com.example.auth.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;

/**
 * @author cjy
 */
@Data
@Component
@ConfigurationProperties(prefix = "shiro")
public class ShiroFilterProperties {
    /**
     * 不鉴权列表
     */
    private List<String> anno = new LinkedList<>();

    /**
     * 鉴权列表
     */
    private List<String> filter = new LinkedList<>();
}
