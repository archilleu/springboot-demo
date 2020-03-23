package com.haoya.demo.app.exception;

/**
 * 自定义异常基类
 */

import org.springframework.http.HttpStatus;

import java.rmi.ServerError;

public class AppException extends RuntimeException {

    public AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public HttpStatus getStatus() { return status; }

    public static final AppException Found = new AppExceptionFound();

    public static final AppException BadRequest = new AppExceptionBadRequest();
    public static final AppException Forbidden = new AppExceptionForbidden();
    public static final AppException NotFound = new AppExceptionNotFound();
    public static final AppException ServerError = new AppExceptionServerError();

    private HttpStatus status;
}
