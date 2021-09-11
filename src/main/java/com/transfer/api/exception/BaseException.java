package com.transfer.api.exception;

public class BaseException extends RuntimeException {

	private static final long serialVersionUID = 1;

	private final ErrorData errorData;

	public BaseException(ErrorData errorData) {
		super();
		this.errorData = errorData;
	}

	public BaseException(ErrorData errorData, Throwable cause) {
		super();
		this.errorData = errorData;
	}

	public ErrorData getErrorData() {
		return this.errorData;
	}

}
