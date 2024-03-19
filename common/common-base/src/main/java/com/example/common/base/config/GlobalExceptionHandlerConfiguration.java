package com.example.common.base.config;

import com.example.common.base.advice.GlobalExceptionHandlerAdvice;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生成配置需要的bean
 *
 * @author cjy
 */
@Configuration
public class GlobalExceptionHandlerConfiguration {
    @Bean
    public GlobalExceptionHandlerAdvice globalDefaultExceptionHandler() {
        return new GlobalExceptionHandlerAdvice();
    }
}
