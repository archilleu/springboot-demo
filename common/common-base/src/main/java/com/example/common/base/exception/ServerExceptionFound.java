package com.example.common.base.exception;

/**
 * @author cjy
 */
public class ServerExceptionFound extends ServerException {
    public ServerExceptionFound() {
        this(null);
    }

    public ServerExceptionFound(String message) {
        super(ResultCode.FOUND, message);
    }
}
