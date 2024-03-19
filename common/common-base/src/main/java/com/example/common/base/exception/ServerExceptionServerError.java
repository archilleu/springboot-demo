package com.example.common.base.exception;

import com.example.common.base.vo.ResultCode;

/**
 * @author cjy
 */
public class ServerExceptionServerError extends ServerException {
    public ServerExceptionServerError() {
        this(null);
    }

    public ServerExceptionServerError(String message) {
        super(ResultCode.INTERNAL_SERVER_ERROR, message);
    }
}
