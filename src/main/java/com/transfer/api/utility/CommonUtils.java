package com.transfer.api.utility;

import static com.transfer.api.utility.TransferAPIServiceConstants.ACCOUNT_NOT_EXISTS;
import static com.transfer.api.utility.TransferAPIServiceConstants.ACCOUNT_NUMBER_LENGHT;
import static com.transfer.api.utility.TransferAPIServiceConstants.DOUBLE_DECIMAL_DIGITS;
import static com.transfer.api.utility.TransferAPIServiceConstants.INVALID_INPUT;
import static com.transfer.api.utility.TransferAPIServiceConstants.IN_SUFFICENT_AMOUNT;
import static com.transfer.api.utility.TransferAPIServiceConstants.NUMBERS_REGREX;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

import com.transfer.api.exception.BaseException;
import com.transfer.api.exception.TransferAPIException;
import com.transfer.api.schema.Account;
import com.transfer.api.schema.Transaction;

@Service
public class CommonUtils {

	public boolean isStringValidLongValue(String data) throws BaseException {
		return (null != data && data.matches(NUMBERS_REGREX) && data.length() == ACCOUNT_NUMBER_LENGHT);
	}

	public boolean isAccountNumberMatching(Account account, String accountNumber, String accountType) {
		if (null == account || null == account.getAccountNumber() || !account.getAccountNumber().equals(accountNumber))
			throw new TransferAPIException(accountType + " " + ACCOUNT_NOT_EXISTS);
		else
			return true;
	}

	public void isTransactionRequestValid(Transaction transaction) throws BaseException {

		if (null == transaction || !isStringValidLongValue(transaction.getSourceAccountNumber())
				|| !isStringValidLongValue(transaction.getDestinationAccountNumber()) || (transaction.getAmount() <= 0))
			throw new TransferAPIException(INVALID_INPUT);

	}

	public void isSourceAccountHasEnoughBalance(Account account, double amount) throws BaseException {

		if (0 == account.getBalance() || account.getBalance() < amount)
			throw new TransferAPIException(IN_SUFFICENT_AMOUNT);

	}

	public LocalDateTime returnLocalDateTime() {
		return LocalDateTime.now();
	}

	public void isAccountRequestValid(Account account) throws BaseException {

		if (null == account || !isStringValidLongValue(account.getAccountNumber()) || (account.getBalance() <= 0))
			throw new TransferAPIException(INVALID_INPUT);

	}

	public double roundedDouble(double input) {
		BigDecimal bd = new BigDecimal(input).setScale(DOUBLE_DECIMAL_DIGITS, RoundingMode.HALF_UP);
		return bd.doubleValue();
	}

}
