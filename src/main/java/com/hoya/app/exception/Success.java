package com.hoya.app.exception;

public class Success {
	
	public Success() {
		this("success");
	}

	public Success(String message) {
		this.msg= message;
	}

	public int getCode() {
		return this.code;
	}
	
	public String getError() {
		return error;
	}
	
	public String getMsg() {
		return this.msg;
	}

	final private int code = 200;
	final private String error = "200 OK";
	private String msg;
	
	public static final Success kSuccess = new Success();
}
