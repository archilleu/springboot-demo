package com.hoya.app.exception;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MaxUploadSizeExceededException;

/**
 * 
 * 
 * @ClassName: AppExceptionHandler
 * 
 * @Description: app异常处理控制类
 * 
 */
@ControllerAdvice
public class AppExceptionHandler {

	/*
	 * 处理继承自BaseException的异常
	 */
	@ExceptionHandler(BaseException.class)
	public ResponseEntity<Error> appRestException(BaseException e) {
		Error error = new Error(e.getCode(), e.getError(), e.getMessage());
		ResponseEntity<Error> entity = new ResponseEntity<Error>(error, HttpStatus.valueOf(e.getCode()));
		return entity;
	}

	/*
	 * 处理文件上传的异常
	 */
	@ExceptionHandler(MaxUploadSizeExceededException.class)
	public ResponseEntity<Error> handleMaxSizeException() {
		Error error = new Error(HttpStatus.FORBIDDEN.value(), HttpStatus.FORBIDDEN.getReasonPhrase(),
				"upload file size limit:" + env.getProperty("spring.servlet.multipart.max-file-size", "1MB"));
		ResponseEntity<Error> entity = new ResponseEntity<Error>(error, HttpStatus.valueOf(error.getCode()));
		return entity;
	}
	
	@Autowired
	private Environment env;
}