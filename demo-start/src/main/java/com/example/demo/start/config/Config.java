package com.example.demo.start.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * @author cjy
 */
@Data
@Configuration
@ConfigurationProperties("spring.datasource")
public class Config {
    private String url;
    private String username;
    private String password;
    private String driverClassName;
    private String dir;
    private String parent;
    private String module;
    private String author;
    private List<String> include;
}
