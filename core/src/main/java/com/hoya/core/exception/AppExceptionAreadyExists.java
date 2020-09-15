package com.hoya.core.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionAreadyExists extends AppException {
    public AppExceptionAreadyExists() {
        this(HttpStatus.NOT_FOUND.toString());
    }

    public AppExceptionAreadyExists(String message) {
        super(HttpStatus.NOT_FOUND, message);
    }
}
