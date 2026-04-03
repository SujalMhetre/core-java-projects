package com.bank.service;

import com.bank.dao.AccountDAO;
import com.bank.dao.AccountDAOImpl;
import com.bank.dao.TransactionDAO;
import com.bank.dao.TransactionDAOImpl;
import com.bank.exception.AccountNotFoundException;
import com.bank.exception.DuplicateAccountException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidTransactionException;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class BankingService {

	private final AccountDAO accountDAO = new AccountDAOImpl();
	private final TransactionDAO transactionDAO = new TransactionDAOImpl();

	// ─── ACCOUNT OPERATIONS ───────────────────────────────────────────────────

	public Account createAccount(String name, String email, BigDecimal initial)
			throws DuplicateAccountException, InvalidTransactionException, SQLException {

		if (name == null || name.trim().isEmpty())
			throw new InvalidTransactionException("Account holder name cannot be empty.");
		if (email == null || !email.contains("@"))
			throw new InvalidTransactionException("Invalid email address.");
		if (initial.compareTo(BigDecimal.ZERO) < 0)
			throw new InvalidTransactionException("Initial deposit cannot be negative.");

		// Check for duplicate email
		Account existing = accountDAO.getAccountByEmail(email);
		if (existing != null)
			throw new DuplicateAccountException(email);

		Account account = new Account(name.trim(), email.trim().toLowerCase(), initial);
		boolean created = accountDAO.createAccount(account);
		if (!created)
			throw new SQLException("Failed to create account — database error.");

		return account;
	}

	/**
	 * Looks up an account by its ID.
	 */
	public Account getAccount(int accountId) throws AccountNotFoundException, SQLException {
		Account acc = accountDAO.getAccountById(accountId);
		if (acc == null)
			throw new AccountNotFoundException(accountId);
		return acc;
	}

	/**
	 * Looks up an account by email.
	 */
	public Account getAccountByEmail(String email) throws AccountNotFoundException, SQLException {
		Account acc = accountDAO.getAccountByEmail(email);
		if (acc == null)
			throw new AccountNotFoundException(email);
		return acc;
	}

	/**
	 * Returns all accounts.
	 */
	public List<Account> getAllAccounts() throws SQLException {
		return accountDAO.getAllAccounts();
	}

	/**
	 * Deletes an account. Requires the balance to be zero first.
	 */
	public boolean deleteAccount(int accountId)
			throws AccountNotFoundException, InvalidTransactionException, SQLException {

		Account acc = getAccount(accountId);
		if (acc.getBalance().compareTo(BigDecimal.ZERO) != 0)
			throw new InvalidTransactionException(
					"Cannot delete account with non-zero balance. Please withdraw remaining ₹" + acc.getBalance()
							+ " first.");

		return accountDAO.deleteAccount(accountId);
	}

	// ─── TRANSACTION OPERATIONS ───────────────────────────────────────────────

	public boolean deposit(int accountId, BigDecimal amount)
			throws AccountNotFoundException, InvalidTransactionException, SQLException {

		validatePositiveAmount(amount);

		try (Connection conn = DBConnection.getConnection()) {
			conn.setAutoCommit(false);
			try {
				Account acc = accountDAO.getAccountById(accountId, conn);
				if (acc == null)
					throw new AccountNotFoundException(accountId);

				BigDecimal newBalance = acc.getBalance().add(amount);
				accountDAO.updateBalance(accountId, newBalance, conn);

				transactionDAO.addTransaction(new Transaction(null, accountId, "CREDIT", amount), conn);

				conn.commit();
				return true;

			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public boolean withdraw(int accountId, BigDecimal amount)
			throws AccountNotFoundException, InsufficientBalanceException, InvalidTransactionException, SQLException {

		validatePositiveAmount(amount);

		try (Connection conn = DBConnection.getConnection()) {
			conn.setAutoCommit(false);
			try {
				Account acc = accountDAO.getAccountById(accountId, conn);
				if (acc == null)
					throw new AccountNotFoundException(accountId);

				if (acc.getBalance().compareTo(amount) < 0)
					throw new InsufficientBalanceException(acc.getBalance().doubleValue(), amount.doubleValue());

				BigDecimal newBalance = acc.getBalance().subtract(amount);
				accountDAO.updateBalance(accountId, newBalance, conn);

				transactionDAO.addTransaction(new Transaction(accountId, null, "DEBIT", amount), conn);

				conn.commit();
				return true;

			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		}
	}

	public boolean transfer(int fromAccountId, int toAccountId, BigDecimal amount)
			throws AccountNotFoundException, InsufficientBalanceException, InvalidTransactionException, SQLException {

		validatePositiveAmount(amount);

		if (fromAccountId == toAccountId)
			throw new InvalidTransactionException("Source and destination accounts must be different.");

		try (Connection conn = DBConnection.getConnection()) {
			conn.setAutoCommit(false);
			try {
				Account fromAcc = accountDAO.getAccountById(fromAccountId, conn);
				Account toAcc = accountDAO.getAccountById(toAccountId, conn);

				if (fromAcc == null)
					throw new AccountNotFoundException(fromAccountId);
				if (toAcc == null)
					throw new AccountNotFoundException(toAccountId);

				if (fromAcc.getBalance().compareTo(amount) < 0)
					throw new InsufficientBalanceException(fromAcc.getBalance().doubleValue(), amount.doubleValue());

				accountDAO.updateBalance(fromAccountId, fromAcc.getBalance().subtract(amount), conn);
				accountDAO.updateBalance(toAccountId, toAcc.getBalance().add(amount), conn);

				transactionDAO.addTransaction(new Transaction(fromAccountId, toAccountId, "TRANSFER", amount), conn);

				conn.commit();
				return true;

			} catch (Exception e) {
				conn.rollback();
				throw e;
			}
		}
	}

	/**
	 * Returns all transactions for a given account.
	 */
	public List<Transaction> getTransactionHistory(int accountId) throws AccountNotFoundException, SQLException {

		if (accountDAO.getAccountById(accountId) == null)
			throw new AccountNotFoundException(accountId);
		return transactionDAO.getTransactionsByAccount(accountId);
	}

	/**
	 * Returns all transactions in the system (admin view).
	 */
	public List<Transaction> getAllTransactions() throws SQLException {
		return transactionDAO.getAllTransactions();
	}

	/**
	 * Returns the current balance of an account.
	 */
	public BigDecimal getBalance(int accountId) throws AccountNotFoundException, SQLException {
		return getAccount(accountId).getBalance();
	}

	/**
	 * Returns a summary map of system statistics.
	 */
	public void printSystemSummary() throws SQLException {
		int totalAccounts = accountDAO.countAccounts();
		int totalTransactions = transactionDAO.countTransactions();
		List<Account> accounts = accountDAO.getAllAccounts();

		BigDecimal totalDeposits = accounts.stream().map(Account::getBalance).reduce(BigDecimal.ZERO, BigDecimal::add);

		System.out.println("\n╔══════════════════════════════════╗");
		System.out.println("║         SYSTEM SUMMARY           ║");
		System.out.println("╠══════════════════════════════════╣");
		System.out.printf("║  Total Accounts     : %10d ║%n", totalAccounts);
		System.out.printf("║  Total Transactions : %10d ║%n", totalTransactions);
		System.out.printf("║  Total Funds (₹)    : %10.2f ║%n", totalDeposits);
		System.out.println("╚══════════════════════════════════╝");
	}

	// ─── HELPERS ──────────────────────────────────────────────────────────────

	private void validatePositiveAmount(BigDecimal amount) throws InvalidTransactionException {
		if (amount == null || amount.compareTo(BigDecimal.ZERO) <= 0)
			throw new InvalidTransactionException("Amount must be greater than zero.");
	}
}