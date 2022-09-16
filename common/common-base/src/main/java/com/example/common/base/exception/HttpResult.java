package com.example.common.base.exception;

import com.example.common.base.advice.GlobalResultResponseAdvice;
import lombok.Data;

/**
 * {@link GlobalResultResponseAdvice} 全局包装返回对象
 *
 * @author cjy
 */

@Data
public class HttpResult {

    private Integer code;
    private String message;
    private Object data;

    public HttpResult(ResultCode resultCode, Object data) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
