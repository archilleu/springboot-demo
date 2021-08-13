package com.starter.common.exception;

public class ServerExceptionFound extends ServerException {
    public ServerExceptionFound() {
        this(null);
    }

    public ServerExceptionFound(String message) {
        super(ResultCode.FOUND, message);
    }
}
