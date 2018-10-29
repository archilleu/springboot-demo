package com.hoya.app.exception;

public class Error {

	public Error(int code, String error, String message) {
		this.code = code;
		this.error = error;
		this.message = message;
	}

	public int getCode() {
		return this.code;
	}
	
	public String getError() {
		return error;
	}

	public String getMessage() {
		return this.message;
	}

	private int code;
	private String error;
	private String message;
}
