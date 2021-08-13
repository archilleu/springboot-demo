package com.starter.base;

import com.starter.common.annotation.EnableGlobalExceptionHandle;
import com.starter.common.annotation.EnableGlobalResultResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableGlobalExceptionHandle
@EnableGlobalResultResponse
@SpringBootApplication
public class StarterApplication {
    public static void main(String[] args) {
        SpringApplication.run(StarterApplication.class, args);
    }
}
