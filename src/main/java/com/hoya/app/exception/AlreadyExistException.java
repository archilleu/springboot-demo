package com.hoya.app.exception;

import org.springframework.http.HttpStatus;

public class AlreadyExistException extends BaseException {

	public AlreadyExistException() {
		this("already exist");
	}

	public AlreadyExistException(String message) {
		this(HttpStatus.FORBIDDEN.getReasonPhrase(), message);
	}

	public AlreadyExistException(String error, String message) {
		super(HttpStatus.FORBIDDEN.value(), error, message);
	}

	private static final long serialVersionUID = 1L;
}
