package com.hoya.core.exception;

public class AppExceptionForbidden extends ServerException {
    public AppExceptionForbidden() {
        this(null);
    }

    public AppExceptionForbidden(String message) {
        super(ResultCode.FORBIDDEN, message);
    }
}
