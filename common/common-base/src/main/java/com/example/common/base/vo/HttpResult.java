package com.example.common.base.vo;

import com.example.common.base.advice.GlobalResultResponseAdvice;
import lombok.Data;

/**
 * {@link GlobalResultResponseAdvice} 全局包装返回对象
 *
 * @author cjy
 */

@Data
public class HttpResult<T> {
    private Integer code;
    private String message;
    private T data;

    public HttpResult(ResultCode resultCode, T data) {
        this.data = data;
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }
}
