package com.starter.common.exception;

import com.starter.common.advice.GlobalExceptionHandlerAdvice;
import lombok.Getter;

/**
 * REST API 错误对象,供{@link GlobalExceptionHandlerAdvice} 使用
 */

@Getter
public class ServerError {
    private int code;
    private String message;
    private Object data;

    public ServerError(int status, String message) {
        this.code = status;
        this.message = message;
    }

    public ServerError(int status, String message, Object data) {
        this(status, message);
        this.data = data;
    }
}
