package com.hoya.core.config;

import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.hoya.core.advice.GlobalExceptionHandlerAdvice;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 生成配置需要的bean
 */
@Configuration
public class EnableGlobalExceptionHandlerConfiguration {
    @Bean
    public GlobalExceptionHandlerAdvice globalDefaultExceptionHandler() {
        return new GlobalExceptionHandlerAdvice();
    }

    @Bean
    public HttpMessageConverters castHttpMessageConverter() {
        return new HttpMessageConverters(new FastJsonHttpMessageConverter());
    }
}
