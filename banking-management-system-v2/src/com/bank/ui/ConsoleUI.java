package com.bank.ui;

import com.bank.exception.AccountNotFoundException;
import com.bank.exception.DuplicateAccountException;
import com.bank.exception.InsufficientBalanceException;
import com.bank.exception.InvalidTransactionException;
import com.bank.model.Account;
import com.bank.model.Transaction;
import com.bank.service.BankingService;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

/**
 * Console-based user interface for the Banking Transaction Management System.
 * Keeps all I/O concerns separate from the service layer.
 */
public class ConsoleUI {

	private final BankingService service = new BankingService();
	private final Scanner scanner = new Scanner(System.in);

	// ─── ENTRY POINT ──────────────────────────────────────────────────────────

	public void start() {
		printBanner();
		boolean running = true;

		while (running) {
			printMainMenu();
			int choice = readInt("Enter choice: ");

			switch (choice) {
			case 1 -> handleCreateAccount();
			case 2 -> handleDeposit();
			case 3 -> handleWithdraw();
			case 4 -> handleTransfer();
			case 5 -> handleCheckBalance();
			case 6 -> handleViewTransactionHistory();
			case 7 -> handleViewAllAccounts();
			case 8 -> handleViewAllTransactions();
			case 9 -> handleDeleteAccount();
			case 10 -> handleSystemSummary();
			case 0 -> {
				System.out.println("\n  Thank you for using Banking System. Goodbye!\n");
				running = false;
			}
			default -> System.out.println("\n  [!] Invalid choice. Please try again.\n");
			}
		}

		scanner.close();
	}

	// ─── MENU HANDLERS ────────────────────────────────────────────────────────

	private void handleCreateAccount() {
		printHeader("CREATE NEW ACCOUNT");
		String name = readString("  Full Name     : ");
		String email = readString("  Email Address : ");
		System.out.print("  Initial Deposit (₹) [0 to skip]: ");
		BigDecimal initial = readDecimal();

		try {
			Account acc = service.createAccount(name, email, initial);
			printSuccess("Account created successfully!");
			System.out.println("  " + acc);
		} catch (DuplicateAccountException e) {
			printError("Email already registered: " + e.getMessage());
		} catch (InvalidTransactionException | SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleDeposit() {
		printHeader("DEPOSIT");
		int accountId = readInt("  Account ID : ");
		System.out.print("  Amount (₹) : ");
		BigDecimal amount = readDecimal();

		try {
			service.deposit(accountId, amount);
			BigDecimal newBalance = service.getBalance(accountId);
			printSuccess(String.format("₹%.2f deposited successfully. New balance: ₹%.2f", amount, newBalance));
		} catch (AccountNotFoundException | InvalidTransactionException | SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleWithdraw() {
		printHeader("WITHDRAW");
		int accountId = readInt("  Account ID : ");
		System.out.print("  Amount (₹) : ");
		BigDecimal amount = readDecimal();

		try {
			service.withdraw(accountId, amount);
			BigDecimal newBalance = service.getBalance(accountId);
			printSuccess(String.format("₹%.2f withdrawn successfully. New balance: ₹%.2f", amount, newBalance));
		} catch (AccountNotFoundException | InsufficientBalanceException | InvalidTransactionException
				| SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleTransfer() {
		printHeader("TRANSFER FUNDS");
		int fromId = readInt("  From Account ID : ");
		int toId = readInt("  To Account ID   : ");
		System.out.print("  Amount (₹)       : ");
		BigDecimal amount = readDecimal();

		try {
			service.transfer(fromId, toId, amount);
			printSuccess(String.format("₹%.2f transferred from Acc#%d to Acc#%d successfully.", amount, fromId, toId));
			System.out.printf("  From balance: ₹%.2f%n", service.getBalance(fromId));
			System.out.printf("  To   balance: ₹%.2f%n", service.getBalance(toId));
		} catch (AccountNotFoundException | InsufficientBalanceException | InvalidTransactionException
				| SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleCheckBalance() {
		printHeader("CHECK BALANCE");
		int accountId = readInt("  Account ID : ");

		try {
			Account acc = service.getAccount(accountId);
			System.out.println();
			System.out.println("  " + acc);
			System.out.printf("  Current Balance: ₹%.2f%n", acc.getBalance());
		} catch (AccountNotFoundException | SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleViewTransactionHistory() {
		printHeader("TRANSACTION HISTORY");
		int accountId = readInt("  Account ID : ");

		try {
			List<Transaction> txns = service.getTransactionHistory(accountId);
			if (txns.isEmpty()) {
				System.out.println("\n  No transactions found for this account.\n");
			} else {
				System.out.printf("%n  Found %d transaction(s):%n%n", txns.size());
				txns.forEach(t -> System.out.println("  " + t));
				System.out.println();
			}
		} catch (AccountNotFoundException | SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleViewAllAccounts() {
		printHeader("ALL ACCOUNTS");

		try {
			List<Account> accounts = service.getAllAccounts();
			if (accounts.isEmpty()) {
				System.out.println("\n  No accounts found.\n");
			} else {
				System.out.printf("%n  Total accounts: %d%n%n", accounts.size());
				accounts.forEach(a -> System.out.println("  " + a));
				System.out.println();
			}
		} catch (SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleViewAllTransactions() {
		printHeader("ALL TRANSACTIONS");

		try {
			List<Transaction> txns = service.getAllTransactions();
			if (txns.isEmpty()) {
				System.out.println("\n  No transactions found.\n");
			} else {
				System.out.printf("%n  Total transactions: %d%n%n", txns.size());
				txns.forEach(t -> System.out.println("  " + t));
				System.out.println();
			}
		} catch (SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleDeleteAccount() {
		printHeader("DELETE ACCOUNT");
		int accountId = readInt("  Account ID : ");
		System.out.print("  Are you sure? (yes/no): ");
		String confirm = scanner.nextLine().trim().toLowerCase();

		if (!confirm.equals("yes")) {
			System.out.println("  [i] Deletion cancelled.\n");
			return;
		}

		try {
			service.deleteAccount(accountId);
			printSuccess("Account #" + accountId + " has been deleted.");
		} catch (AccountNotFoundException | InvalidTransactionException | SQLException e) {
			printError(e.getMessage());
		}
	}

	private void handleSystemSummary() {
		try {
			service.printSystemSummary();
		} catch (SQLException e) {
			printError(e.getMessage());
		}
	}

	// ─── UI HELPERS ───────────────────────────────────────────────────────────

	private void printBanner() {
		System.out.println();
		System.out.println("  ╔═══════════════════════════════════════════════╗");
		System.out.println("  ║    BANKING TRANSACTION MANAGEMENT SYSTEM      ║");
		System.out.println("  ║           Powered by Java + MySQL             ║");
		System.out.println("  ╚═══════════════════════════════════════════════╝");
		System.out.println();
	}

	private void printMainMenu() {
		System.out.println("  ┌─────────────────────────────┐");
		System.out.println("  │           MAIN MENU         │");
		System.out.println("  ├─────────────────────────────┤");
		System.out.println("  │  1. Create Account          │");
		System.out.println("  │  2. Deposit                 │");
		System.out.println("  │  3. Withdraw                │");
		System.out.println("  │  4. Transfer Funds          │");
		System.out.println("  │  5. Check Balance           │");
		System.out.println("  │  6. Transaction History     │");
		System.out.println("  │  7. View All Accounts       │");
		System.out.println("  │  8. View All Transactions   │");
		System.out.println("  │  9. Delete Account          │");
		System.out.println("  │ 10. System Summary          │");
		System.out.println("  │  0. Exit                    │");
		System.out.println("  └─────────────────────────────┘");
	}

	private void printHeader(String title) {
		System.out.println();
		System.out.println("  ── " + title + " ──");
		System.out.println();
	}

	private void printSuccess(String msg) {
		System.out.println("\n  ✔ " + msg + "\n");
	}

	private void printError(String msg) {
		System.out.println("\n  ✘ ERROR: " + msg + "\n");
	}

	private int readInt(String prompt) {
		while (true) {
			System.out.print(prompt);
			try {
				return Integer.parseInt(scanner.nextLine().trim());
			} catch (NumberFormatException e) {
				System.out.println("  [!] Please enter a valid integer.");
			}
		}
	}

	private BigDecimal readDecimal() {
		while (true) {
			try {
				String input = scanner.nextLine().trim();
				BigDecimal value = new BigDecimal(input);
				if (value.compareTo(BigDecimal.ZERO) < 0) {
					System.out.print("  [!] Amount cannot be negative. Try again: ");
					continue;
				}
				return value;
			} catch (NumberFormatException e) {
				System.out.print("  [!] Invalid amount. Enter a number (e.g. 500 or 1500.50): ");
			}
		}
	}

	private String readString(String prompt) {
		while (true) {
			System.out.print(prompt);
			String value = scanner.nextLine().trim();
			if (!value.isEmpty())
				return value;
			System.out.println("  [!] This field cannot be empty.");
		}
	}
}