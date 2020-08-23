package com.hoya.core.exception;

/**
 * 自定义异常基类
 */

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class AppException extends RuntimeException {

    public AppException(HttpStatus status, String message) {
        super(message);
        this.status = status;
    }

    public static final AppException Found = new AppExceptionFound();

    public static final AppException BadRequest = new AppExceptionBadRequest();
    public static final AppException Forbidden = new AppExceptionForbidden();
    public static final AppException NotFound = new AppExceptionNotFound();
    public static final AppException ServerError = new AppExceptionServerError();

    private HttpStatus status;
}
