package com.example.common.base.exception;

/**
 * @author cjy
 */
public class ServerExceptionUnauthorized extends ServerException {
    public ServerExceptionUnauthorized() {
        this(null);
    }

    public ServerExceptionUnauthorized(String message) {
        super(ResultCode.UNAUTHORIZED, message);
    }
}
