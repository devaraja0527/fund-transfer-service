package com.transfer.api.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.transfer.api.schema.Transaction;

public interface TransactionRepository extends CrudRepository<Transaction, Long> {

	@Query(value = "SELECT coalesce(MAX(transcationId), 0) FROM Transaction")
	public Long getMaxTransactionId();

}
