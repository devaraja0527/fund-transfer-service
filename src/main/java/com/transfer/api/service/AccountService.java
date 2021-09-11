package com.transfer.api.service;

import com.transfer.api.schema.Account;

public interface AccountService {

	public Account newAccount();

	public Account getAccountDetail(String accountNumber);
}
