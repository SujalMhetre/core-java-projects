package com.bank.exception;

public class AccountNotFoundException extends Exception {

	private final int accountId;

	public AccountNotFoundException(int accountId) {
		super("Account not found with ID: " + accountId);
		this.accountId = accountId;
	}

	public AccountNotFoundException(String email) {
		super("Account not found with email: " + email);
		this.accountId = -1;
	}

	public int getAccountId() {
		return accountId;
	}
}