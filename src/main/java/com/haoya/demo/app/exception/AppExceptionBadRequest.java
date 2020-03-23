package com.haoya.demo.app.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionBadRequest extends AppException {
    public AppExceptionBadRequest() {
        this(HttpStatus.BAD_REQUEST.toString());
    }

    public AppExceptionBadRequest(String message) {
        super(HttpStatus.BAD_REQUEST, message);
    }
}
