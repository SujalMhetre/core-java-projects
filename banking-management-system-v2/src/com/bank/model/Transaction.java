package com.bank.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Transaction {

	private int transactionId;
	private Integer fromAccount;
	private Integer toAccount;
	private String type; // CREDIT, DEBIT, TRANSFER
	private BigDecimal amount;
	private Timestamp createdAt;

	// Constructors
	public Transaction() {
	}

	public Transaction(Integer fromAccount, Integer toAccount, String type, BigDecimal amount) {
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.type = type;
		this.amount = amount;
	}

	public Transaction(int transactionId, Integer fromAccount, Integer toAccount, String type, BigDecimal amount,
			Timestamp createdAt) {
		this.transactionId = transactionId;
		this.fromAccount = fromAccount;
		this.toAccount = toAccount;
		this.type = type;
		this.amount = amount;
		this.createdAt = createdAt;
	}

	// Getters and Setters
	public int getTransactionId() {
		return transactionId;
	}

	public void setTransactionId(int transactionId) {
		this.transactionId = transactionId;
	}

	public Integer getFromAccount() {
		return fromAccount;
	}

	public void setFromAccount(Integer fromAccount) {
		this.fromAccount = fromAccount;
	}

	public Integer getToAccount() {
		return toAccount;
	}

	public void setToAccount(Integer toAccount) {
		this.toAccount = toAccount;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		String from = fromAccount != null ? "Acc#" + fromAccount : "EXTERNAL";
		String to = toAccount != null ? "Acc#" + toAccount : "EXTERNAL";
		return String.format("Txn [ID: %5d | %-8s | From: %-10s | To: %-10s | ₹%10.2f | %s]", transactionId, type, from,
				to, amount, createdAt != null ? createdAt.toString().substring(0, 19) : "N/A");
	}
}