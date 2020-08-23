package com.hoya.core.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpResult {
    public HttpResult(Object data) {
        this(data, "success");
    }

    public HttpResult(Object data, String message) {
        this.data = data;
        this.message = message;
    }

    final private static int status = HttpStatus.OK.value();
    private String message;
    private Object data;
}
