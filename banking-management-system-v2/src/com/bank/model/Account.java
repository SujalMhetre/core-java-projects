package com.bank.model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Account {

	private int accountId;
	private String name;
	private String email;
	private BigDecimal balance;
	private Timestamp createdAt;

	// Constructors
	public Account() {
	}

	public Account(String name, String email, BigDecimal balance) {
		this.name = name;
		this.email = email;
		this.balance = balance;
	}

	public Account(int accountId, String name, String email, BigDecimal balance, Timestamp createdAt) {
		this.accountId = accountId;
		this.name = name;
		this.email = email;
		this.balance = balance;
		this.createdAt = createdAt;
	}

	// Getters and Setters
	public int getAccountId() {
		return accountId;
	}

	public void setAccountId(int accountId) {
		this.accountId = accountId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public Timestamp getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Timestamp createdAt) {
		this.createdAt = createdAt;
	}

	@Override
	public String toString() {
		return String.format("Account [ID: %d | Name: %-20s | Email: %-30s | Balance: ₹%12.2f | Joined: %s]", accountId,
				name, email, balance, createdAt != null ? createdAt.toString().substring(0, 10) : "N/A");
	}
}