package com.bank.exception;

public class DuplicateAccountException extends Exception {

	public DuplicateAccountException(String email) {
		super("An account already exists with email: " + email);
	}
}