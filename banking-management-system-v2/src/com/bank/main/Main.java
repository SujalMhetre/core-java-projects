package com.bank.main;

import com.bank.ui.ConsoleUI;
import com.bank.util.DBConnection;

public class Main {

	public static void main(String[] args) {
		System.out.println("\n  Connecting to database...");

		if (!DBConnection.testConnection()) {
			System.err.println("\n  [ERROR] Could not connect to the database.");
			System.err.println("  Please check:");
			System.err.println("    - MySQL is running on localhost:3306");
			System.err.println("    - Database 'banking_system' exists (run schema.sql)");
			System.err.println("    - Credentials in src/main/resources/db.properties are correct");
			System.exit(1);
		}

		System.out.println("  Database connected successfully.\n");

		new ConsoleUI().start();
	}
}