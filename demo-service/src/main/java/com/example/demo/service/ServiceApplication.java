package com.example.demo.service;

import com.example.common.base.annotation.EnableGlobalExceptionHandle;
import com.example.common.base.annotation.EnableGlobalResultResponse;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;

/**
 * @author cjy
 */
@Controller
@EnableCaching
@MapperScan("com.example.demo.service.mapper")
@EnableGlobalExceptionHandle
@EnableGlobalResultResponse
@SpringBootApplication(scanBasePackages = ServiceApplication.PACKAGES)
public class ServiceApplication extends SpringBootServletInitializer {
    /**
     * 显示指定扫描类,包含common模块
     */
    final static public String PACKAGES = "com.example";

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(ServiceApplication.class);
    }

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

}
