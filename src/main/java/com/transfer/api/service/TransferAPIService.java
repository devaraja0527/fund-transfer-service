package com.transfer.api.service;

import com.transfer.api.exception.BaseException;
import com.transfer.api.schema.Account;
import com.transfer.api.schema.Transaction;
import com.transfer.api.schema.TransactionResponse;

public interface TransferAPIService {

	TransactionResponse transfer(Transaction transaction) throws BaseException;

	TransactionResponse creditMoney(Account account) throws BaseException;

}
