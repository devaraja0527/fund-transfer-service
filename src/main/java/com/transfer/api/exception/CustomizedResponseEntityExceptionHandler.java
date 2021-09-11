package com.transfer.api.exception;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageConversionException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@RestControllerAdvice
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	private static final Logger logger = LogManager.getLogger(CustomizedResponseEntityExceptionHandler.class);

	@Override
	protected ResponseEntity<Object> handleServletRequestBindingException(ServletRequestBindingException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.error("ServletRequestBindingException " + ex.getMessage());
		ErrorData errorData = getErrorDataOBJ(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("HttpRequestMethodNotSupportedException [{}]", ex.getMessage());
		ErrorData errorData = getErrorDataOBJ(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("MethodArgumentNotValidException [{}] - WebRequest[{}]", ex.getMessage(), request);
		ErrorData errorData = getErrorDataOBJ(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("HttpMessageNotReadableException [{}]", ex.getMessage());
		ErrorData errorData = getErrorDataOBJ(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);

	}

	@Override
	protected ResponseEntity<Object> handleHttpMediaTypeNotSupported(HttpMediaTypeNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		logger.info("HttpMediaTypeNotSupportedException [{}]", ex.getMessage());
		ErrorData errorData = getErrorDataOBJ(HttpStatus.UNSUPPORTED_MEDIA_TYPE.value(), ex.getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.UNSUPPORTED_MEDIA_TYPE);

	}

	@ExceptionHandler({ HttpMessageConversionException.class, MethodArgumentTypeMismatchException.class })
	public final ResponseEntity<Object> handleInvalidRequestDataException(Exception ex, WebRequest request) {
		logger.info("{}HttpMessageConversionException [{}]", ex.getClass().getSimpleName(), ex.getMessage());
		ErrorData errorData = getErrorDataOBJ(HttpStatus.BAD_REQUEST.value(), ex.getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.BAD_REQUEST);

	}

	@ExceptionHandler(BaseException.class)
	public final ResponseEntity<Object> handleExceptions(BaseException ex) {
		logger.error("Exception [{}]", ex.getErrorData().getMessage());
		ErrorData errorData = getErrorDataOBJ(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getErrorData().getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleExceptions(Exception ex, WebRequest request) {
		logger.error("Exception [{}]", ex.getMessage(), ex);
		ErrorData errorData = getErrorDataOBJ(HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getMessage());
		return new ResponseEntity<>(errorData, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	private ErrorData getErrorDataOBJ(Integer statusCode, String message) {
		return new ErrorData(statusCode, String.valueOf(statusCode), message);

	}

}
