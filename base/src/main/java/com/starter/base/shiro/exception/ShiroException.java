package com.starter.base.shiro.exception;

import com.starter.common.exception.ServerExceptionUnauthorized;

public class ShiroException extends ServerExceptionUnauthorized {
    public ShiroException(String msg) {
        super(msg);
    }
}
