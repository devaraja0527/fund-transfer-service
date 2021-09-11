package com.transfer.api.controller;

import javax.validation.Valid;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.transfer.api.schema.Account;
import com.transfer.api.schema.Transaction;
import com.transfer.api.schema.TransactionResponse;
import com.transfer.api.service.TransferAPIService;

@RestController
public class TransferAPIController implements TransferAPI {

	@Autowired
	TransferAPIService corebankingservice;

	Logger logger = LogManager.getLogger(TransferAPIController.class);

	@Override
	public ResponseEntity<TransactionResponse> transferMoney(@Valid Transaction transaction) {
		logger.trace("Transfer request Received!");
		TransactionResponse transferResponse = corebankingservice.transfer(transaction);
		logger.trace("Transfer Completed!");
		return new ResponseEntity<TransactionResponse>(transferResponse, HttpStatus.CREATED);

	}

	@Override
	public ResponseEntity<TransactionResponse> creditBalance(@Valid Account account) {

		logger.trace("Credit Money request Received!");
		TransactionResponse transferResponse = corebankingservice.creditMoney(account);
		logger.trace("Credit Money Completed!");
		return new ResponseEntity<TransactionResponse>(transferResponse, HttpStatus.CREATED);

	}

}
