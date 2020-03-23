package com.haoya.demo.app.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionForbidden extends AppException {
    public AppExceptionForbidden() {
        this(HttpStatus.FORBIDDEN.toString());
    }

    public AppExceptionForbidden(String message) {
        super(HttpStatus.FORBIDDEN, message);
    }
}
