package com.hoya.core.exception;

public class AppExceptionNotFound extends ServerException {
    public AppExceptionNotFound() {
        this(null);
    }

    public AppExceptionNotFound(String message) {
        super(ResultCode.NOT_FOUND, message);
    }
}
