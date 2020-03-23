package com.haoya.demo.app.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionServerError extends AppException {
    public AppExceptionServerError() {
        this(HttpStatus.INTERNAL_SERVER_ERROR.toString());
    }

    public AppExceptionServerError(String message) {
        super(HttpStatus.INTERNAL_SERVER_ERROR, message);
    }
}
