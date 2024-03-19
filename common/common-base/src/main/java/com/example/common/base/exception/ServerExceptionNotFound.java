package com.example.common.base.exception;

import com.example.common.base.vo.ResultCode;

/**
 * @author cjy
 */
public class ServerExceptionNotFound extends ServerException {
    public ServerExceptionNotFound() {
        this(null);
    }

    public ServerExceptionNotFound(String message) {
        super(ResultCode.NOT_FOUND, message);
    }
}
