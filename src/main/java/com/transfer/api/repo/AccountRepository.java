package com.transfer.api.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.transfer.api.schema.Account;

public interface AccountRepository extends CrudRepository<Account, Long> {

	@Query(value = "SELECT a FROM Account a WHERE a.accountNumber = ?1")
	public Account findByAccountNumber(String accountNumber);

	@Query(value = "SELECT coalesce(MAX(accountId), 0) FROM Account")
	public Long getMaxAccountId();
}
