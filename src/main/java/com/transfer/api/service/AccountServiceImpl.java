package com.transfer.api.service;

import static com.transfer.api.utility.TransferAPIServiceConstants.ACCOUNT_NUMBER_PREORDER;
import static com.transfer.api.utility.TransferAPIServiceConstants.INITIAL_BALANCE;
import static com.transfer.api.utility.TransferAPIServiceConstants.INVALID_INPUT;
import static com.transfer.api.utility.TransferAPIServiceConstants.SOURCE;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transfer.api.exception.TransferAPIException;
import com.transfer.api.repo.AccountRepository;
import com.transfer.api.schema.Account;
import com.transfer.api.utility.CommonUtils;

@Service
public class AccountServiceImpl implements AccountService {

	Logger logger = LogManager.getLogger(AccountServiceImpl.class);

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private CommonUtils commonUtils;

	@Override
	public Account newAccount() {

		Account account = new Account();
		try {

			long id = (accountRepository.getMaxAccountId() + 1);
			account.setAccountId(id);
			account.setAccountNumber(String.format(ACCOUNT_NUMBER_PREORDER, id));
			account.setBalance(INITIAL_BALANCE);

			accountRepository.save(account);

		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TransferAPIException(e.getMessage());
		}
		return account;
	}

	@Override
	public Account getAccountDetail(String accountNumber) {

		Account account = null;

		if (!commonUtils.isStringValidLongValue(accountNumber))
			throw new TransferAPIException(INVALID_INPUT);

		Account sourceAccount = accountRepository.findByAccountNumber(accountNumber);
		if (commonUtils.isAccountNumberMatching(sourceAccount, accountNumber, SOURCE))
			account = sourceAccount;

		return account;
	}

}
