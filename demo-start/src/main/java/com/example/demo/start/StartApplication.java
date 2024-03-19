package com.example.demo.start;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

/**
 * @author cjy
 */
@SpringBootApplication(scanBasePackages = StartApplication.PACKAGES, exclude = {DataSourceAutoConfiguration.class})
public class StartApplication {
    final static public String PACKAGES = "com.example";

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
