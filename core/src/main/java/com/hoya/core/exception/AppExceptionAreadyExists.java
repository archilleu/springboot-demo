package com.hoya.core.exception;

import org.springframework.http.HttpStatus;

public class AppExceptionAreadyExists extends AppException {
    public AppExceptionAreadyExists() {
        this(HttpStatus.FOUND.toString());
    }

    public AppExceptionAreadyExists(String message) {
        super(HttpStatus.FOUND, message);
    }
}
