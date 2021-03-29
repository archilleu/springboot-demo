package com.hoya.core.exception;

public class AppExceptionServerError extends ServerException {
    public AppExceptionServerError() {
        this(null);
    }

    public AppExceptionServerError(String message) {
        super(ResultCode.INTERNAL_SERVER_ERROR, message);
    }
}
