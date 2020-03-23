package com.haoya.demo.app.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionNotFound extends AppException {
    public AppExceptionNotFound() {
        this(HttpStatus.NOT_FOUND.toString());
    }

    public AppExceptionNotFound(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
