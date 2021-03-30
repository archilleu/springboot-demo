package com.hoya;

import com.hoya.core.annotation.EnableGlobalExceptionHandle;
import com.hoya.core.annotation.EnableGlobalResultResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@EnableGlobalExceptionHandle
@EnableGlobalResultResponse
@SpringBootApplication(exclude= {DataSourceAutoConfiguration.class})
public class BackupApplication {
    public static void main(String[] args) {
        SpringApplication.run(BackupApplication.class, args);
    }
}
