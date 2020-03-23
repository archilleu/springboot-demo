package com.haoya.demo.app.exception;

/**
 * REST api 错误对象
 */

public class AppError {
    public AppError(int status, String message) {
        this.status = status;
        this.message = message;
    }

    public int getStatus() { return status; }
    public String getMessage() { return message; }

    private int status;
    private String message;
}
