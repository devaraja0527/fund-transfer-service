package com.transfer.api.controller;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.transfer.api.exception.ErrorData;
import com.transfer.api.schema.Account;
import com.transfer.api.schema.Transaction;
import com.transfer.api.schema.TransactionResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

public interface TransferAPI {

	@ApiOperation(value = "Transfer money from one account to another", nickname = "Transfer", notes = "This API will send money", tags = {
			"transfer-api" })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = TransactionResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorData.class),
			@ApiResponse(code = 401, message = "Unauthorised", response = ErrorData.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorData.class),
			@ApiResponse(code = 404, message = "Entity Not Found", response = ErrorData.class),
			@ApiResponse(code = 405, message = "Method Not Allowed", response = ErrorData.class),
			@ApiResponse(code = 406, message = "Not Acceptable", response = ErrorData.class),
			@ApiResponse(code = 429, message = "Too Many Requests", response = ErrorData.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorData.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorData.class) })
	@PostMapping(value = "/transfer", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<TransactionResponse> transferMoney(@RequestBody @Valid Transaction transaction);

	@ApiOperation(value = "Credit Money to the Given Account", nickname = "CreditMoney", notes = "This API will Credit Money to the Given Account", tags = {
			"transfer-api" })
	@ApiResponses(value = { @ApiResponse(code = 201, message = "Created", response = TransactionResponse.class),
			@ApiResponse(code = 400, message = "Bad Request", response = ErrorData.class),
			@ApiResponse(code = 401, message = "Unauthorised", response = ErrorData.class),
			@ApiResponse(code = 403, message = "Forbidden", response = ErrorData.class),
			@ApiResponse(code = 404, message = "Entity Not Found", response = ErrorData.class),
			@ApiResponse(code = 405, message = "Method Not Allowed", response = ErrorData.class),
			@ApiResponse(code = 406, message = "Not Acceptable", response = ErrorData.class),
			@ApiResponse(code = 429, message = "Too Many Requests", response = ErrorData.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ErrorData.class),
			@ApiResponse(code = 503, message = "Service Unavailable", response = ErrorData.class) })
	@PostMapping(value = "/credit", produces = MediaType.APPLICATION_JSON_VALUE)
	@ResponseStatus(HttpStatus.CREATED)
	ResponseEntity<TransactionResponse> creditBalance(@RequestBody @Valid Account account);

}
