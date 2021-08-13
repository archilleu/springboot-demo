package com.starter.common.exception;

public class ServerExceptionServerError extends ServerException {
    public ServerExceptionServerError() {
        this(null);
    }

    public ServerExceptionServerError(String message) {
        super(ResultCode.INTERNAL_SERVER_ERROR, message);
    }
}
