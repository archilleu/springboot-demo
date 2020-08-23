package com.hoya.admin.config;

import com.hoya.admin.security.JwtInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class MyWebMvcConfigurer implements WebMvcConfigurer {

    /**
     * 拦截必要的URL进行授权检测
     *
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new JwtInterceptor()).addPathPatterns("/**").excludePathPatterns(
                "/druid/**",
                "/",
                "/login",
                "/swagger-ui.html",
                "/swagger-resources/**",
                "/v2/api-docs",
                "/webjars/springfox-swagger-ui/**"
        );
    }

    /**
     * 跨域配置，不使用cookie不需要
     */

}