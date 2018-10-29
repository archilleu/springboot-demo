package com.hoya.app.exception;

import org.springframework.http.HttpStatus;

public class NotFoundException extends BaseException {

	public NotFoundException() {
		this("not found");
	}

	public NotFoundException(String message) {
		super(HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase(), message);
	}

	public NotFoundException(String error, String message) {
		super(HttpStatus.NOT_FOUND.value(), error, message);
	}

	private static final long serialVersionUID = 1L;
}
