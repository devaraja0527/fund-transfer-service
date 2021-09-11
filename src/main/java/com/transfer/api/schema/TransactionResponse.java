package com.transfer.api.schema;

import java.io.Serializable;
import java.time.LocalDateTime;

public class TransactionResponse implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private long transactionId;

	private String sourceAccountNumber;

	private double sourceAccountBalance;

	private LocalDateTime transactionDateTime;

	public TransactionResponse() {

	}

	public TransactionResponse(long transactionId, String sourceAccountNumber, double sourceAccountBalance,
			LocalDateTime transactionDateTime) {
		super();
		this.transactionId = transactionId;
		this.sourceAccountNumber = sourceAccountNumber;
		this.sourceAccountBalance = sourceAccountBalance;
		this.transactionDateTime = transactionDateTime;
	}

	public long getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(long transactionId) {
		this.transactionId = transactionId;
	}

	public String getSourceAccountNumber() {
		return sourceAccountNumber;
	}

	public void setSourceAccountNumber(String sourceAccountNumber) {
		this.sourceAccountNumber = sourceAccountNumber;
	}

	public double getSourceAccountBalance() {
		return sourceAccountBalance;
	}

	public void setSourceAccountBalance(double sourceAccountBalance) {
		this.sourceAccountBalance = sourceAccountBalance;
	}

	public LocalDateTime getTransactionDateTime() {
		return transactionDateTime;
	}

	public void setTransactionDateTime(LocalDateTime transactionDateTime) {
		this.transactionDateTime = transactionDateTime;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		long temp;
		temp = Double.doubleToLongBits(sourceAccountBalance);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((sourceAccountNumber == null) ? 0 : sourceAccountNumber.hashCode());
		result = prime * result + ((transactionDateTime == null) ? 0 : transactionDateTime.hashCode());
		result = prime * result + (int) (transactionId ^ (transactionId >>> 32));
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
		TransactionResponse other = (TransactionResponse) obj;
		if (Double.doubleToLongBits(sourceAccountBalance) != Double.doubleToLongBits(other.sourceAccountBalance))
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
		if (transactionId != other.transactionId)
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "TransactionResponse [transactionId=" + transactionId + ", sourceAccountNumber=" + sourceAccountNumber
				+ ", sourceAccountBalance=" + sourceAccountBalance + ", transactionDateTime=" + transactionDateTime
				+ "]";
	}

}
