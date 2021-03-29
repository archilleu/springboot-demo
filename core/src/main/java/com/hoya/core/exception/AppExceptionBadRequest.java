package com.hoya.core.exception;

public class AppExceptionBadRequest extends ServerException {
    public AppExceptionBadRequest() {
        this(null);
    }

    public AppExceptionBadRequest(String message) {
        super(ResultCode.BAD_REQUEST, message);
    }
}
