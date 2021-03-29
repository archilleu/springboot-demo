package com.hoya.core.exception;

/**
 * 自定义异常基类
 */

import lombok.Getter;

@Getter
public class ServerException extends RuntimeException {

    public ServerException(ResultCode resultCode) {
        this(resultCode, null);
    }

    public ServerException(ResultCode resultCode, String message) {
        if(null == message) {
            this.message = resultCode.getMessage();
        } else {
            this.message = message;
        }

        this.code = resultCode.getCode();
    }

    public static final ServerException Found = new AppExceptionFound();

    public static final ServerException BadRequest = new AppExceptionBadRequest();
    public static final ServerException Forbidden = new AppExceptionForbidden();
    public static final ServerException NotFound = new AppExceptionNotFound();
    public static final ServerException ServerError = new AppExceptionServerError();

    private Integer code;
    private String message;
}
