package com.starter.common.exception;

public class ServerExceptionUnauthorized extends ServerException {
    public ServerExceptionUnauthorized() {
        this(null);
    }

    public ServerExceptionUnauthorized(String message) {
        super(ResultCode.UNAUTHORIZED, message);
    }
}
