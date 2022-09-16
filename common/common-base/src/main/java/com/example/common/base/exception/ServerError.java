package com.example.common.base.exception;

import com.example.common.base.advice.GlobalExceptionHandlerAdvice;
import lombok.Getter;

/**
 * REST API 错误对象,供{@link GlobalExceptionHandlerAdvice} 使用
 *
 * @author cjy
 */

@Getter
public class ServerError {
    private final int code;
    private final String message;

    public ServerError(int status, String message) {
        this.code = status;
        this.message = message;
    }

}
