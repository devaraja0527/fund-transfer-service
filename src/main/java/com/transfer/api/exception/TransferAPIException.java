package com.transfer.api.exception;

public class TransferAPIException extends BaseException {

	private static final long serialVersionUID = 1L;

	public TransferAPIException(String message) {
		super(new ErrorData(message));
	}

	public TransferAPIException(String message, int statusCode) {
		super(new ErrorData(statusCode, String.valueOf(statusCode), message));
	}

}