package com.transfer.api.service;

import static com.transfer.api.utility.TransferAPIServiceConstants.DESTINATION;
import static com.transfer.api.utility.TransferAPIServiceConstants.SOURCE;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.transfer.api.exception.BaseException;
import com.transfer.api.repo.AccountRepository;
import com.transfer.api.repo.TransactionRepository;
import com.transfer.api.schema.Account;
import com.transfer.api.schema.Transaction;
import com.transfer.api.schema.TransactionResponse;
import com.transfer.api.schema.TransactionType;
import com.transfer.api.utility.CommonUtils;

@Service
public class TransferAPIServiceImpl implements TransferAPIService {

	@Autowired
	private AccountRepository accountRepository;

	@Autowired
	private TransactionRepository transactionRepository;

	@Autowired
	private CommonUtils commonUtils;

	@Override
	public TransactionResponse transfer(Transaction transaction) throws BaseException {

		TransactionResponse transactionResponse = null;

		commonUtils.isTransactionRequestValid(transaction);

		Account sourceAccount = accountRepository.findByAccountNumber(transaction.getSourceAccountNumber());

		if (commonUtils.isAccountNumberMatching(sourceAccount, transaction.getSourceAccountNumber(), SOURCE)) {

			commonUtils.isSourceAccountHasEnoughBalance(sourceAccount, transaction.getAmount());

			Account destAccount = accountRepository.findByAccountNumber(transaction.getDestinationAccountNumber());
			if (commonUtils.isAccountNumberMatching(destAccount, transaction.getDestinationAccountNumber(),
					DESTINATION)) {

				long existingTansId = transactionRepository.getMaxTransactionId();

				LocalDateTime localDateTime = commonUtils.returnLocalDateTime();

				double sourceAcctBalance = sourceAccount.getBalance() - transaction.getAmount();
				double destAcctBalance = destAccount.getBalance() + transaction.getAmount();

				transaction.setTranscationId((existingTansId + 1));
				transaction.setTransactionDateTime(localDateTime);
				transaction.setTransactionType(TransactionType.DEBIT);
				transactionRepository.save(transaction);

				sourceAccount.setBalance(sourceAcctBalance);
				destAccount.setBalance(destAcctBalance);
				accountRepository.save(destAccount);
				accountRepository.save(sourceAccount);

				transactionResponse = new TransactionResponse((existingTansId + 1), sourceAccount.getAccountNumber(),
						commonUtils.roundedDouble(sourceAcctBalance), localDateTime);

			}
		}

		return transactionResponse;
	}

	@Override
	public TransactionResponse creditMoney(Account account) throws BaseException {
		TransactionResponse transactionResponse = null;

		commonUtils.isAccountRequestValid(account);
		Account sourceAccount = accountRepository.findByAccountNumber(account.getAccountNumber());
		if (commonUtils.isAccountNumberMatching(sourceAccount, account.getAccountNumber(), SOURCE)) {

			long existingTansId = transactionRepository.getMaxTransactionId();
			LocalDateTime localDateTime = commonUtils.returnLocalDateTime();

			Transaction transaction = new Transaction((existingTansId + 1), sourceAccount.getAccountNumber(), null,
					account.getBalance(), localDateTime, TransactionType.CREDIT);

			transactionRepository.save(transaction);

			double newbalance = account.getBalance() + sourceAccount.getBalance();
			sourceAccount.setBalance(newbalance);
			accountRepository.save(sourceAccount);

			transactionResponse = new TransactionResponse((existingTansId + 1), sourceAccount.getAccountNumber(),
					commonUtils.roundedDouble(newbalance), localDateTime);

		}
		return transactionResponse;
	}

}
