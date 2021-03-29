package com.hoya.core.exception;

public class AppExceptionUnauthorized extends ServerException {
    public AppExceptionUnauthorized() {
        this(null);
    }

    public AppExceptionUnauthorized(String message) {
        super(ResultCode.UNAUTHORIZED, message); }
}
