package com.example.common.base.exception;

import com.example.common.base.vo.ResultCode;

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
