package com.example.common.base.exception;

import com.example.common.base.vo.ResultCode;

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
