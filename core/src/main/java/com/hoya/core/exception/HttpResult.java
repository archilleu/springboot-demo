package com.hoya.core.exception;

import lombok.Data;

@Data
public class HttpResult {

    public HttpResult(ResultCode resultCode, Object data) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    private Integer code;
    private String message;
    private Object data;
}
