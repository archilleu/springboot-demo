package com.example.common.base.vo;

import com.example.common.base.exception.ResultCode;
import lombok.Data;

/**
 * @author cjy
 */
@Data
public class HttpResult<T> {
    private int code;
    private String message;
    private T data;

    public HttpResult(ResultCode resultCode, T data) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = getMessage();
    }
}
