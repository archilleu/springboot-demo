package com.hoya.app.exception;

import org.springframework.http.HttpStatus;

public class BadRequestException extends BaseException {

	public BadRequestException() {
		this("bad request");
	}
	
	public BadRequestException(String message) {
		super(HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase(), message);
	}

	public BadRequestException(String error, String message) {
		super(HttpStatus.BAD_REQUEST.value(), error, message);
	}

	private static final long serialVersionUID = 1L;
}
