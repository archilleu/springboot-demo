package com.example.common.base.exception;

import com.example.common.base.vo.ResultCode;

/**
 * @author cjy
 */
public class ServerExceptionBadRequest extends ServerException {
    public ServerExceptionBadRequest() {
        this(null);
    }

    public ServerExceptionBadRequest(String message) {
        super(ResultCode.BAD_REQUEST, message);
    }
}
