package com.example.common.base.config;

import com.example.common.base.advice.GlobalResultResponseAdvice;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生成配置需要的bean
 *
 * @author cjy
 */
@Configuration
@EnableConfigurationProperties(GlobalDefaultProperties.class)
public class GlobalResultResponseConfiguration {

    @Bean
    public GlobalResultResponseAdvice globalResponseDataAdvice(GlobalDefaultProperties globalDefaultProperties) {
        return new GlobalResultResponseAdvice(globalDefaultProperties);
    }

}
