package com.example.common.base.exception;

import com.example.common.base.vo.ResultCode;

/**
 * @author cjy
 */
public class ServerExceptionForbidden extends ServerException {
    public ServerExceptionForbidden() {
        this(null);
    }

    public ServerExceptionForbidden(String message) {
        super(ResultCode.FORBIDDEN, message);
    }
}
