package com.haoya.demo.app.exception;

/**
 * REST api内部定义异常处理控制器
 */

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

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
}
