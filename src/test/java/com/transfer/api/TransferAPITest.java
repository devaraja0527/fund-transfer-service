package com.transfer.api;

import java.net.URI;
import java.net.URISyntaxException;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

import com.transfer.api.schema.Account;
import com.transfer.api.schema.Transaction;
import com.transfer.api.schema.TransactionResponse;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class TransferAPITest {

	@LocalServerPort
	int randomServerPort;

	HttpHeaders headers = null;
	String baseUrl = null;

	RestTemplate restTemplate = null;

	URI creditUri = null;

	URI accountUri = null;
	URI transferUri = null;

	@BeforeAll
	void setUp() throws URISyntaxException {
		headers = new HttpHeaders();
		headers.set("Content-Type", "application/json");
		baseUrl = "http://localhost:" + randomServerPort + "/TransferAPI/v1";
		restTemplate = new RestTemplate();

		creditUri = new URI(baseUrl + "/credit");
		accountUri = new URI(baseUrl + "/account");

		transferUri = new URI(baseUrl + "/transfer");
	}

	@Test
	void credit_money_positive_test() throws Exception {

		ResponseEntity<Account> accountResult = restTemplate.getForEntity(accountUri, Account.class);
		Account account = accountResult.getBody();
		account.setBalance(300);
		HttpEntity<Account> request = new HttpEntity<>(account, headers);
		ResponseEntity<TransactionResponse> result = restTemplate.postForEntity(creditUri, request,
				TransactionResponse.class);
		Assertions.assertEquals(201, result.getStatusCodeValue());
		Assertions.assertEquals(300, result.getBody().getSourceAccountBalance());
	}

	@Test
	void credit_money_null_account_400_test() throws Exception {
		try {
			Account account = null;
			HttpEntity<Account> request = new HttpEntity<>(account, headers);
			ResponseEntity<Account> result = restTemplate.postForEntity(creditUri, request, Account.class);
		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(400, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Required request body is missing"));
		}
	}

	@Test
	void credit_money_account_invalid_accountnumber_500__test() throws Exception {
		try {
			Account account = new Account();
			account.setAccountNumber("sssssss");
			HttpEntity<Account> request = new HttpEntity<>(account, headers);
			ResponseEntity<Account> result = restTemplate.postForEntity(creditUri, request, Account.class);
		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Invalid Input provided!!"));
		}
	}

	@Test
	void credit_money_account_lessdigit_accountnumber_500__test() throws Exception {
		try {
			Account account = new Account();
			account.setAccountNumber("0011");
			HttpEntity<Account> request = new HttpEntity<>(account, headers);
			ResponseEntity<Account> result = restTemplate.postForEntity(creditUri, request, Account.class);
		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Invalid Input provided!!"));
		}
	}

	@Test
	void credit_money_account_accountnumber_doesnt_exists_500__test() throws Exception {
		try {
			Account account = new Account();
			account.setAccountNumber("00001111");
			account.setBalance(10);
			HttpEntity<Account> request = new HttpEntity<>(account, headers);
			ResponseEntity<Account> result = restTemplate.postForEntity(creditUri, request, Account.class);
		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Source Account Doesnt exists!!"));
		}
	}

	@Test
	void transfer_api_positive_testing() throws Exception {

		Transaction transaction = new Transaction();
		ResponseEntity<Account> accountResult = restTemplate.getForEntity(accountUri, Account.class);
		Account account = accountResult.getBody();
		account.setBalance(300);

		HttpEntity<Account> request = new HttpEntity<>(account, headers);
		ResponseEntity<TransactionResponse> creditMoney = restTemplate.postForEntity(creditUri, request, TransactionResponse.class);

		transaction.setSourceAccountNumber(account.getAccountNumber());
		transaction.setAmount(100);

		accountResult = restTemplate.getForEntity(accountUri, Account.class);
		account = accountResult.getBody();
		transaction.setDestinationAccountNumber(account.getAccountNumber());

		HttpEntity<Transaction> transrequest = new HttpEntity<>(transaction, headers);
		ResponseEntity<TransactionResponse> tranResponseEntity = restTemplate.postForEntity(transferUri, transrequest,
				TransactionResponse.class);

		Assertions.assertEquals(201, tranResponseEntity.getStatusCodeValue());
		Assertions.assertEquals(200, tranResponseEntity.getBody().getSourceAccountBalance());

	}

	@Test
	void transfer_api_400_bad_request_test() throws Exception {

		try {
			Transaction transaction = null;
			HttpEntity<Transaction> transrequest = new HttpEntity<>(transaction, headers);

			ResponseEntity<TransactionResponse> tranResponseEntity = restTemplate.postForEntity(transferUri,
					transrequest, TransactionResponse.class);

		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(400, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Required request body is missing"));
		}
	}

	@Test
	void transfer_api_invalid_transaction_request_test() throws Exception {

		try {
			Transaction transaction = new Transaction();
			transaction.setDestinationAccountNumber("test0022");
			HttpEntity<Transaction> transrequest = new HttpEntity<>(transaction, headers);

			ResponseEntity<TransactionResponse> tranResponseEntity = restTemplate.postForEntity(transferUri,
					transrequest, TransactionResponse.class);

		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Invalid Input provided!!"));
		}
	}

	@Test
	void transfer_api_invalid_source_account_doesnt_exist_request_test() throws Exception {

		try {
			Transaction transaction = new Transaction();
			transaction.setSourceAccountNumber("00001111");
			transaction.setDestinationAccountNumber("00001112");
			transaction.setAmount(100);
			HttpEntity<Transaction> transrequest = new HttpEntity<>(transaction, headers);

			ResponseEntity<TransactionResponse> tranResponseEntity = restTemplate.postForEntity(transferUri,
					transrequest, TransactionResponse.class);

		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Source Account Doesnt exists!!"));
		}
	}

	@Test
	void transfer_api_not_enough_funds_request_test() throws Exception {
		try {
			Transaction transaction = new Transaction();
			ResponseEntity<Account> accountResult = restTemplate.getForEntity(accountUri, Account.class);
			Account account = accountResult.getBody();
			account.setBalance(300);

			HttpEntity<Account> request = new HttpEntity<>(account, headers);
			ResponseEntity<TransactionResponse> creditMoney = restTemplate.postForEntity(creditUri, request, TransactionResponse.class);

			transaction.setSourceAccountNumber(account.getAccountNumber());
			transaction.setDestinationAccountNumber("10000001");
			transaction.setAmount(1000);

			HttpEntity<Transaction> transrequest = new HttpEntity<>(transaction, headers);

			ResponseEntity<TransactionResponse> tranResponseEntity = restTemplate.postForEntity(transferUri,
					transrequest, TransactionResponse.class);

		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("In Suffiecent Amount in the source Account!!"));
		}
	}

	@Test
	void transfer_api_destination_account_deoent_exists_request_test() throws Exception {
		try {
			Transaction transaction = new Transaction();
			ResponseEntity<Account> accountResult = restTemplate.getForEntity(accountUri, Account.class);
			Account account = accountResult.getBody();
			account.setBalance(400);

			HttpEntity<Account> request = new HttpEntity<>(account, headers);
			ResponseEntity<TransactionResponse> creditMoney = restTemplate.postForEntity(creditUri, request, TransactionResponse.class);

			transaction.setSourceAccountNumber(account.getAccountNumber());
			transaction.setDestinationAccountNumber("10000001");
			transaction.setAmount(300);

			HttpEntity<Transaction> transrequest = new HttpEntity<>(transaction, headers);

			ResponseEntity<TransactionResponse> tranResponseEntity = restTemplate.postForEntity(transferUri,
					transrequest, TransactionResponse.class);

		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Destination Account Doesnt exists!!"));
		}
	}


	@Test
	void transfer_api_invalid_destination_account_request_test() throws Exception {
		try {
			Transaction transaction = new Transaction();
			ResponseEntity<Account> accountResult = restTemplate.getForEntity(accountUri, Account.class);
			Account account = accountResult.getBody();
			account.setBalance(400);

			HttpEntity<Account> request = new HttpEntity<>(account, headers);
			ResponseEntity<TransactionResponse> creditMoney = restTemplate.postForEntity(creditUri, request, TransactionResponse.class);

			transaction.setSourceAccountNumber(account.getAccountNumber());
			transaction.setDestinationAccountNumber("testment000111");
			transaction.setAmount(300);

			HttpEntity<Transaction> transrequest = new HttpEntity<>(transaction, headers);

			ResponseEntity<TransactionResponse> tranResponseEntity = restTemplate.postForEntity(transferUri,
					transrequest, TransactionResponse.class);

		} catch (HttpStatusCodeException e) {
			Assertions.assertEquals(500, e.getRawStatusCode());
			Assertions.assertTrue(e.getMessage().contains("Invalid Input provided!!"));
		}
	}
}
