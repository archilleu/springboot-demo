package com.hoya.admin;

import com.hoya.core.annotation.EnableGlobalExceptionHandle;
import com.hoya.core.annotation.EnableGlobalResultResponse;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@EnableGlobalExceptionHandle
@EnableGlobalResultResponse
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

}
