package com.bank.dao;

import com.bank.model.Transaction;
import com.bank.util.DBConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class TransactionDAOImpl implements TransactionDAO {

	private static final String INSERT_SQL = "INSERT INTO transactions (from_account, to_account, type, amount) VALUES (?, ?, ?, ?)";

	// ─── CREATE ────────────────────────────────────────────────────────────────

	@Override
	public boolean addTransaction(Transaction txn) throws SQLException {
		try (Connection conn = DBConnection.getConnection()) {
			return addTransaction(txn, conn);
		}
	}

	@Override
	public boolean addTransaction(Transaction txn, Connection conn) throws SQLException {
		try (PreparedStatement ps = conn.prepareStatement(INSERT_SQL, Statement.RETURN_GENERATED_KEYS)) {

			if (txn.getFromAccount() != null)
				ps.setInt(1, txn.getFromAccount());
			else
				ps.setNull(1, Types.INTEGER);

			if (txn.getToAccount() != null)
				ps.setInt(2, txn.getToAccount());
			else
				ps.setNull(2, Types.INTEGER);

			ps.setString(3, txn.getType());
			ps.setBigDecimal(4, txn.getAmount());

			int affected = ps.executeUpdate();
			if (affected == 0)
				return false;

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					txn.setTransactionId(rs.getInt(1));
			}
			return true;
		}
	}

	// ─── READ ──────────────────────────────────────────────────────────────────

	@Override
	public Transaction getTransactionById(int transactionId) throws SQLException {
		String sql = "SELECT * FROM transactions WHERE transaction_id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, transactionId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapRow(rs) : null;
			}
		}
	}

	@Override
	public List<Transaction> getTransactionsByAccount(int accountId) throws SQLException {
		List<Transaction> list = new ArrayList<>();
		String sql = "SELECT * FROM transactions WHERE from_account = ? OR to_account = ? ORDER BY created_at DESC";

		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, accountId);
			ps.setInt(2, accountId);
			try (ResultSet rs = ps.executeQuery()) {
				while (rs.next())
					list.add(mapRow(rs));
			}
		}
		return list;
	}

	@Override
	public List<Transaction> getAllTransactions() throws SQLException {
		List<Transaction> list = new ArrayList<>();
		String sql = "SELECT * FROM transactions ORDER BY created_at DESC";

		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next())
				list.add(mapRow(rs));
		}
		return list;
	}

	@Override
	public int countTransactions() throws SQLException {
		String sql = "SELECT COUNT(*) FROM transactions";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			return rs.next() ? rs.getInt(1) : 0;
		}
	}

	// ─── HELPER ───────────────────────────────────────────────────────────────

	private Transaction mapRow(ResultSet rs) throws SQLException {
		Transaction txn = new Transaction();
		txn.setTransactionId(rs.getInt("transaction_id"));

		int from = rs.getInt("from_account");
		txn.setFromAccount(rs.wasNull() ? null : from);

		int to = rs.getInt("to_account");
		txn.setToAccount(rs.wasNull() ? null : to);

		txn.setType(rs.getString("type"));
		txn.setAmount(rs.getBigDecimal("amount"));
		txn.setCreatedAt(rs.getTimestamp("created_at"));
		return txn;
	}
}