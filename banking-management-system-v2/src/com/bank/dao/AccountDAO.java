package com.bank.dao;

import com.bank.model.Account;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface AccountDAO {

	boolean createAccount(Account account) throws SQLException;

	Account getAccountById(int accountId) throws SQLException;

	Account getAccountById(int accountId, Connection conn) throws SQLException;

	Account getAccountByEmail(String email) throws SQLException;

	boolean updateBalance(int accountId, BigDecimal newBalance) throws SQLException;

	boolean updateBalance(int accountId, BigDecimal newBalance, Connection conn) throws SQLException;

	List<Account> getAllAccounts() throws SQLException;

	boolean deleteAccount(int accountId) throws SQLException;

	int countAccounts() throws SQLException;
}