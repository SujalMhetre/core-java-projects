package com.bank.dao;

import com.bank.model.Account;
import com.bank.util.DBConnection;

import java.math.BigDecimal;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AccountDAOImpl implements AccountDAO {

	// ─── CREATE ────────────────────────────────────────────────────────────────

	@Override
	public boolean createAccount(Account account) throws SQLException {
		String sql = "INSERT INTO accounts (name, email, balance) VALUES (?, ?, ?)";
		try (Connection conn = DBConnection.getConnection();
				PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

			ps.setString(1, account.getName());
			ps.setString(2, account.getEmail());
			ps.setBigDecimal(3, account.getBalance());

			int affected = ps.executeUpdate();
			if (affected == 0)
				return false;

			try (ResultSet rs = ps.getGeneratedKeys()) {
				if (rs.next())
					account.setAccountId(rs.getInt(1));
			}
			return true;
		}
	}

	// ─── READ ──────────────────────────────────────────────────────────────────

	@Override
	public Account getAccountById(int accountId) throws SQLException {
		try (Connection conn = DBConnection.getConnection()) {
			return getAccountById(accountId, conn);
		}
	}

	@Override
	public Account getAccountById(int accountId, Connection conn) throws SQLException {
		String sql = "SELECT * FROM accounts WHERE account_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setInt(1, accountId);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapRow(rs) : null;
			}
		}
	}

	@Override
	public Account getAccountByEmail(String email) throws SQLException {
		String sql = "SELECT * FROM accounts WHERE email = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setString(1, email);
			try (ResultSet rs = ps.executeQuery()) {
				return rs.next() ? mapRow(rs) : null;
			}
		}
	}

	@Override
	public List<Account> getAllAccounts() throws SQLException {
		List<Account> accounts = new ArrayList<>();
		String sql = "SELECT * FROM accounts ORDER BY account_id";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			while (rs.next())
				accounts.add(mapRow(rs));
		}
		return accounts;
	}

	@Override
	public int countAccounts() throws SQLException {
		String sql = "SELECT COUNT(*) FROM accounts";
		try (Connection conn = DBConnection.getConnection();
				Statement stmt = conn.createStatement();
				ResultSet rs = stmt.executeQuery(sql)) {

			return rs.next() ? rs.getInt(1) : 0;
		}
	}

	// ─── UPDATE ────────────────────────────────────────────────────────────────

	@Override
	public boolean updateBalance(int accountId, BigDecimal newBalance) throws SQLException {
		try (Connection conn = DBConnection.getConnection()) {
			return updateBalance(accountId, newBalance, conn);
		}
	}

	@Override
	public boolean updateBalance(int accountId, BigDecimal newBalance, Connection conn) throws SQLException {
		String sql = "UPDATE accounts SET balance = ? WHERE account_id = ?";
		try (PreparedStatement ps = conn.prepareStatement(sql)) {
			ps.setBigDecimal(1, newBalance);
			ps.setInt(2, accountId);
			return ps.executeUpdate() > 0;
		}
	}

	// ─── DELETE ────────────────────────────────────────────────────────────────

	@Override
	public boolean deleteAccount(int accountId) throws SQLException {
		String sql = "DELETE FROM accounts WHERE account_id = ?";
		try (Connection conn = DBConnection.getConnection(); PreparedStatement ps = conn.prepareStatement(sql)) {

			ps.setInt(1, accountId);
			return ps.executeUpdate() > 0;
		}
	}

	// ─── HELPER ───────────────────────────────────────────────────────────────

	private Account mapRow(ResultSet rs) throws SQLException {
		Account acc = new Account();
		acc.setAccountId(rs.getInt("account_id"));
		acc.setName(rs.getString("name"));
		acc.setEmail(rs.getString("email"));
		acc.setBalance(rs.getBigDecimal("balance"));
		acc.setCreatedAt(rs.getTimestamp("created_at"));
		return acc;
	}
}