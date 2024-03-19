package com.example.auth.exception;

import com.example.common.base.exception.ServerExceptionUnauthorized;

/**
 * @author cjy
 */
public class ShiroException extends ServerExceptionUnauthorized {
    public ShiroException(String msg) {
        super(msg);
    }
}
