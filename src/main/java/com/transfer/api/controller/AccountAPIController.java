package com.transfer.api.controller;

import static org.springframework.http.ResponseEntity.ok;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.transfer.api.schema.Account;
import com.transfer.api.service.AccountService;

@RestController
public class AccountAPIController implements AccountAPI {

	Logger logger = LogManager.getLogger(AccountAPIController.class);

	@Autowired
	private AccountService accountService;

	@Override
	public ResponseEntity<Account> newAccount() {

		logger.trace("New Account request Received!");
		Account account = accountService.newAccount();
		logger.trace("New Account request Completed!");
		return ok(account);
	}

	@Override
	public ResponseEntity<Account> getbalance(String accountNumber) {

		logger.trace("Get Account balance request Received!");
		Account account = accountService.getAccountDetail(accountNumber);
		logger.trace("Get Account balance request Completed!");
		return ok(account);
	}

}
