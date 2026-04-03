package com.bank.dao;

import com.bank.model.Transaction;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public interface TransactionDAO {

	boolean addTransaction(Transaction txn) throws SQLException;

	boolean addTransaction(Transaction txn, Connection conn) throws SQLException;

	List<Transaction> getTransactionsByAccount(int accountId) throws SQLException;

	List<Transaction> getAllTransactions() throws SQLException;

	Transaction getTransactionById(int transactionId) throws SQLException;

	int countTransactions() throws SQLException;
}