package com.transfer.api;

import static org.hamcrest.CoreMatchers.containsString;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.transfer.api.exception.TransferAPIException;
import com.transfer.api.repo.AccountRepository;
import com.transfer.api.schema.Account;
import com.transfer.api.service.AccountService;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AccounAPITest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private AccountRepository accountRepository;

	@Autowired
	private AccountService accountService;

	@Test
	public void newAccount_postive_test() throws Exception {

		mvc.perform(get("/account").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk()).andExpect(content().string(containsString("accountNumber")));
	}

	@Test
	public void newAccount_negative_test() throws Exception {

		when(accountRepository.getMaxAccountId()).thenThrow(new TransferAPIException("Illegal Error"));

		mvc.perform(get("/account").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andExpect(content().string(containsString("500")));
	}

	@Test
	public void account_balance_invalid_accountnumber_negative_test() throws Exception {

		mvc.perform(get("/account/001").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andExpect(content().string(containsString("500")));
	}

	@Test
	public void account_balance_null_accountnumber_negative_test() throws Exception {

		mvc.perform(get("/account/null").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andExpect(content().string(containsString("500")));
	}

	@Test
	public void account_balance_account_doestexists_negative_test() throws Exception {

		mvc.perform(get("/account/10000000").accept(MediaType.APPLICATION_JSON).contentType(MediaType.APPLICATION_JSON))
				.andExpect(status().isInternalServerError()).andExpect(content().string(containsString("500")));
	}

	@Test
	public void account_balance_postive_test() throws Exception {
		Account account = accountService.newAccount();
		when(accountRepository.findByAccountNumber(Mockito.any())).thenReturn(account);
		mvc.perform(get("/account/" + account.getAccountNumber()).accept(MediaType.APPLICATION_JSON)
				.contentType(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(content().string(containsString("0")));
	}

}
