package com.example.api;

import com.example.common.base.annotation.EnableGlobalExceptionHandle;
import com.example.common.base.annotation.EnableGlobalResultResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author cjy
 */
@Controller
@EnableCaching
@EnableGlobalExceptionHandle
@EnableGlobalResultResponse
@SpringBootApplication(scanBasePackages = ServiceApplication.PACKAGES)
public class ServiceApplication {
    /**
     * 显示指定扫描类
     */
    final static public String PACKAGES = "com.example";

    public static void main(String[] args) {
        SpringApplication.run(ServiceApplication.class, args);
    }

    @GetMapping("/docs")
    public String swagger() {
        return "redirect:/swagger-ui/index.html";
    }
}
