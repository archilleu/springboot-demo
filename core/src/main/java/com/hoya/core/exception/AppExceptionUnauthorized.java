package com.hoya.core.exception;

public class AppExceptionUnauthorized extends AppException {
    public AppExceptionUnauthorized() {
        this(null);
    }

    public AppExceptionUnauthorized(String message) {
        super(ResultCode.UNAUTHORIZED, message); }
}
