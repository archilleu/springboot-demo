package com.example.common.base.exception;

import lombok.Getter;

/**
 * @author cjy
 */
@Getter
public class ServerException extends RuntimeException {

    public static final ServerException FOUND = new ServerExceptionFound();
    public static final ServerException BAD_REQUEST = new ServerExceptionBadRequest();
    public static final ServerException UNAUTHORIZED = new ServerExceptionUnauthorized();
    public static final ServerException Forbidden = new ServerExceptionForbidden();
    public static final ServerException NotFound = new ServerExceptionNotFound();
    public static final ServerException ServerError = new ServerExceptionServerError();
    private final String message;
    private Integer code;

    public ServerException(ResultCode resultCode) {
        this(resultCode, null);
    }

    public ServerException(ResultCode resultCode, String message) {
        if (null == message) {
            this.message = resultCode.getMessage();
        } else {
            this.message = message;
        }

        this.code = resultCode.getCode();
    }
}
