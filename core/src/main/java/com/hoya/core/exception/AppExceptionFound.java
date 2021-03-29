package com.hoya.core.exception;

public class AppExceptionFound extends ServerException {
    public AppExceptionFound() {
        this(null);
    }

    public AppExceptionFound(String message) {
        super(ResultCode.FOUND, message);
    }
}
