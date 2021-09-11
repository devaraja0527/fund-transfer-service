package com.transfer.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.transfer.api.exception.ErrorData;
import com.transfer.api.schema.Account;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface AccountAPI {

	@ApiOperation(value = "Create New Account", nickname = "NewAccount", notes = "This API will Create New Account", tags = {
			"account-api" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful", response = Account.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorData.class),
			@ApiResponse(code = 401, message = "Unauthorised", response = ErrorData.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorData.class),
			@ApiResponse(code = 404, message = "Entity Not Found", response = ErrorData.class),
			@ApiResponse(code = 405, message = "Method Not Allowed", response = ErrorData.class),
			@ApiResponse(code = 406, message = "Not Acceptable", response = ErrorData.class),
			@ApiResponse(code = 429, message = "Too Many Requests", response = ErrorData.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorData.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorData.class) })
	@GetMapping(value = "/account", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<Account> newAccount();

	@ApiOperation(value = "Get Balance From Given Account", nickname = "GetBalance", notes = "This API will Get Balance From Given Account", tags = {
			"account-api" })
	@ApiResponses(value = { @ApiResponse(code = 200, message = "Successful", response = Account.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorData.class),
			@ApiResponse(code = 401, message = "Unauthorised", response = ErrorData.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorData.class),
			@ApiResponse(code = 404, message = "Entity Not Found", response = ErrorData.class),
			@ApiResponse(code = 405, message = "Method Not Allowed", response = ErrorData.class),
			@ApiResponse(code = 406, message = "Not Acceptable", response = ErrorData.class),
			@ApiResponse(code = 429, message = "Too Many Requests", response = ErrorData.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorData.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorData.class) })
	@GetMapping(value = "/account/{accountNumber}", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.OK)
	ResponseEntity<Account> getbalance(
			@ApiParam(value = "Account Number", required = true) @PathVariable("accountNumber") String accountNumber);
}
