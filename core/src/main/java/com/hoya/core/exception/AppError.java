package com.hoya.core.exception;

import lombok.Getter;
import  com.hoya.core.advice.GlobalExceptionHandlerAdvice;

/**
 * REST API 错误对象,供{@link GlobalExceptionHandlerAdvice} 使用
 */

@Getter
public class AppError {
    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public AppError(int status, String message, Object data) {
        this(status, message);
        this.data = data;
    }

    private int status;
    private String message;
    private Object data;
}
