package com.hoya.admin.controller;

/**
 * REST api内部定义异常处理控制器
 */

import com.hoya.core.exception.AppError;
import com.hoya.core.exception.AppException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class AppExceptionControllerHandler {

    @ExceptionHandler(AppException.class)
    public ResponseEntity<AppError> appException(AppException ex) {
        AppError error = new AppError(ex.getStatus().value(), ex.getMessage());
        ResponseEntity<AppError> entity = new ResponseEntity<AppError>(error, ex.getStatus());
        return entity;
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<AppError> exception(IllegalArgumentException ex) {
        AppError error = new AppError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        ResponseEntity<AppError> entity = new ResponseEntity<AppError>(error, HttpStatus.BAD_REQUEST);
        return entity;
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<AppError> exception(HttpMessageNotReadableException ex) {
        AppError error = new AppError(HttpStatus.BAD_REQUEST.value(), "序列化bean失败");
        ResponseEntity<AppError> entity = new ResponseEntity<AppError>(error, HttpStatus.BAD_REQUEST);
        return entity;
    }

}