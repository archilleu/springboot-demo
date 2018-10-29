package com.hoya.app.exception;

public class BaseException extends RuntimeException {

	public BaseException(int code, String error, String message) {
		super(message);
		this.code = code;
		this.error = error;
	}
	
	public int getCode() {
		return code;
	}
	
	public String getError() {
		return error;
	}

	private static final long serialVersionUID = 1L;

	private int code;
	private String error;
}
