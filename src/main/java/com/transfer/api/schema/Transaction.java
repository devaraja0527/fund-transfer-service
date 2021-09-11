package com.transfer.api.schema;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Id;

import org.springframework.validation.annotation.Validated;

import com.fasterxml.jackson.annotation.JsonProperty;

import io.swagger.annotations.ApiModelProperty;

@Validated
@Entity
public class Transaction implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Id
	@ApiModelProperty(required = false, hidden = true)
	private long transcationId;

	@JsonProperty("sourceAccountNumber")
	private String sourceAccountNumber;

	@JsonProperty("destinationAccountNumber")
	private String destinationAccountNumber;

	@JsonProperty("amount")
	private double amount;

	@ApiModelProperty(required = false, hidden = true)
	private LocalDateTime transactionDateTime;

	@ApiModelProperty(required = false, hidden = true)
	private TransactionType transactionType;

	public Transaction() {

	}

	public Transaction(long transcationId, String sourceAccountNumber, String destinationAccountNumber, double amount,
			LocalDateTime transactionDateTime, TransactionType transactionType) {
		super();
		this.transcationId = transcationId;
		this.sourceAccountNumber = sourceAccountNumber;
		this.destinationAccountNumber = destinationAccountNumber;
		this.amount = amount;
		this.transactionDateTime = transactionDateTime;
		this.transactionType = transactionType;
	}

	public long getTranscationId() {
		return transcationId;
	}

	public void setTranscationId(long transcationId) {
		this.transcationId = transcationId;
	}

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public String getDestinationAccountNumber() {
		return destinationAccountNumber;
	}

	public void setDestinationAccountNumber(String destinationAccountNumber) {
		this.destinationAccountNumber = destinationAccountNumber;
	}

	public double getAmount() {
		return amount;
	}

	public void setAmount(double amount) {
		this.amount = amount;
	}

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	public TransactionType getTransactionType() {
		return transactionType;
	}

	public void setTransactionType(TransactionType transactionType) {
		this.transactionType = transactionType;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(amount);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((destinationAccountNumber == null) ? 0 : destinationAccountNumber.hashCode());
		result = prime * result + ((sourceAccountNumber == null) ? 0 : sourceAccountNumber.hashCode());
		result = prime * result + ((transactionDateTime == null) ? 0 : transactionDateTime.hashCode());
		result = prime * result + ((transactionType == null) ? 0 : transactionType.hashCode());
		result = prime * result + (int) (transcationId ^ (transcationId >>> 32));
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Transaction other = (Transaction) obj;
		if (Double.doubleToLongBits(amount) != Double.doubleToLongBits(other.amount))
			return false;
		if (destinationAccountNumber == null) {
			if (other.destinationAccountNumber != null)
				return false;
		} else if (!destinationAccountNumber.equals(other.destinationAccountNumber))
			return false;
		if (sourceAccountNumber == null) {
			if (other.sourceAccountNumber != null)
				return false;
		} else if (!sourceAccountNumber.equals(other.sourceAccountNumber))
			return false;
		if (transactionDateTime == null) {
			if (other.transactionDateTime != null)
				return false;
		} else if (!transactionDateTime.equals(other.transactionDateTime))
			return false;
		if (transactionType != other.transactionType)
			return false;
		if (transcationId != other.transcationId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Transaction [transcationId=" + transcationId + ", sourceAccountNumber=" + sourceAccountNumber
				+ ", destinationAccountNumber=" + destinationAccountNumber + ", amount=" + amount
				+ ", transactionDateTime=" + transactionDateTime + ", transactionType=" + transactionType + "]";
	}

}
