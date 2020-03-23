package com.haoya.demo.app.exception;

/**
 * REST api内部定义异常处理控制器
 */

import org.springframework.http.ResponseEntity;
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
}
