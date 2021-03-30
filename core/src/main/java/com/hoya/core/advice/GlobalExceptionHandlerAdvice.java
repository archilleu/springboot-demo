package com.hoya.core.advice;

import com.hoya.core.annotation.EnableGlobalExceptionHandle;
import com.hoya.core.exception.ServerError;
import com.hoya.core.exception.ServerException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * {@link GlobalExceptionHandlerAdvice}全局异常处理,处理 {@link EnableGlobalExceptionHandle}注解
 */

@RestControllerAdvice
public class GlobalExceptionHandlerAdvice {

    @ExceptionHandler(ServerException.class)
    public ResponseEntity<ServerError> appException(ServerException ex) {
        ServerError error = new ServerError(ex.getCode(), ex.getMessage());
        return new ResponseEntity<ServerError>(error, HttpStatus.valueOf(ex.getCode()/10));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ServerError> exception(IllegalArgumentException ex) {
        ServerError error = new ServerError(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
        return new ResponseEntity<ServerError>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class)
    public ResponseEntity<ServerError> exception(HttpMessageNotReadableException ex) {
        ServerError error = new ServerError(HttpStatus.BAD_REQUEST.value(), "序列化bean失败");
        return new ResponseEntity<ServerError>(error, HttpStatus.BAD_REQUEST);
    }

}