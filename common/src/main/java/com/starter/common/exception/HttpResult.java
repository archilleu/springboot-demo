package com.starter.common.exception;

import com.starter.common.advice.GlobalResultResponseAdvice;
import lombok.Data;

/**
 * {@link GlobalResultResponseAdvice} 全局包装返回对象
 */

@Data
public class HttpResult {

    private Integer code;
    private String message;
    private Object data;

    public HttpResult() {

    }

    public HttpResult(ResultCode resultCode, Object data) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
