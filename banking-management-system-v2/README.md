# 🏦 Banking Transaction Management System

A console-based **Banking Transaction Management System** built with **Java** and **MySQL**, following a clean layered architecture (DAO → Service → UI). Designed as a portfolio project demonstrating core Java backend skills: JDBC, transaction management, custom exceptions, and separation of concerns.

---

## 📋 Table of Contents

- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
- [Usage](#usage)
- [Architecture](#architecture)
- [Key Design Decisions](#key-design-decisions)
- [Future Improvements](#future-improvements)

---

## ✨ Features

- **Account Management** — Create, view, and delete bank accounts
- **Deposits & Withdrawals** — Credit and debit operations with balance validation
- **Fund Transfers** — Atomic transfers between accounts with rollback on failure
- **Transaction History** — View full history per account or all system transactions
- **Balance Enquiry** — Real-time balance check for any account
- **System Summary** — Overview of total accounts, transactions, and funds
- **Input Validation** — Guards against negative amounts, duplicate emails, self-transfers, and insufficient balance
- **Custom Exceptions** — Meaningful error messages for every failure scenario
- **Atomic Transactions** — All money operations use JDBC transaction management (`commit`/`rollback`)

---

## 🛠 Tech Stack

| Layer      | Technology                  |
|------------|-----------------------------|
| Language   | Java 11                     |
| Database   | MySQL 8.x                   |
| Connector  | MySQL Connector/J 8.0.33    |                    
| Pattern    | DAO + Service + UI (Layered)|

---

## 📁 Project Structure

```
banking_system/                      
├── schema.sql                       # Database setup script
└── src/
    └── main/
        ├── java/com/bank/
        │   ├── dao/
        │   │   ├── AccountDAO.java           # Account data access interface
        │   │   ├── AccountDAOImpl.java       # JDBC implementation
        │   │   ├── TransactionDAO.java       # Transaction data access interface
        │   │   └── TransactionDAOImpl.java   # JDBC implementation
        │   ├── exception/
        │   │   ├── AccountNotFoundException.java
        │   │   ├── DuplicateAccountException.java
        │   │   ├── InsufficientBalanceException.java
        │   │   └── InvalidTransactionException.java
        │   ├── main/
        │   │   └── Main.java                # Entry point
        │   ├── model/
        │   │   ├── Account.java             # Account entity
        │   │   └── Transaction.java         # Transaction entity
        │   ├── service/
        │   │   └── BankingService.java      # Business logic layer
        │   ├── ui/
        │   │   └── ConsoleUI.java           # Console menu and I/O
        │   └── util/
        │       └── DBConnection.java        # JDBC connection utility                   
```

---

## 🗄 Database Schema

```sql
CREATE DATABASE IF NOT EXISTS banking_system;
USE banking_system;

CREATE TABLE accounts (
    account_id INT AUTO_INCREMENT PRIMARY KEY,
    name       VARCHAR(100)    NOT NULL,
    email      VARCHAR(100)    NOT NULL UNIQUE,
    balance    DECIMAL(12, 2)  NOT NULL DEFAULT 0.00,
    created_at TIMESTAMP       DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE transactions (
    transaction_id INT AUTO_INCREMENT PRIMARY KEY,
    from_account   INT NULL,
    to_account     INT NULL,
    type           ENUM('CREDIT', 'DEBIT', 'TRANSFER') NOT NULL,
    amount         DECIMAL(12, 2) NOT NULL CHECK (amount > 0),
    created_at     TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (from_account) REFERENCES accounts(account_id),
    FOREIGN KEY (to_account)   REFERENCES accounts(account_id)
);
```

**Transaction type semantics:**

| Type       | `from_account` | `to_account` | Description                    |
|------------|----------------|--------------|--------------------------------|
| `CREDIT`   | `NULL`         | Account ID   | External deposit into account  |
| `DEBIT`    | Account ID     | `NULL`       | Withdrawal from account        |
| `TRANSFER` | Account ID     | Account ID   | Transfer between two accounts  |

---

## 🚀 Getting Started

### Prerequisites

- Java 11 or higher
- MySQL 8.x running on `localhost:3306`

### 1. Clone the repository

```bash
git clone https://github.com/your-username/banking-system.git
cd banking-system
```

### 2. Set up the database

```bash
mysql -u root -p < schema.sql
```

### 3. Configure database credentials

Edit `src/main/resources/db.properties`:

```properties
db.url=jdbc:mysql://localhost:3306/banking_system?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC
db.user=root
db.password=your_password_here
```

### 4. Build the project

```bash
mvn clean package
```

### 5. Run the application

```bash
java -jar target/banking-system.jar
```

---

## 💻 Usage

When launched, the application presents an interactive console menu:

```
  ╔═══════════════════════════════════════════════╗
  ║    BANKING TRANSACTION MANAGEMENT SYSTEM      ║
  ║           Powered by Java + MySQL             ║
  ╚═══════════════════════════════════════════════╝

  ┌─────────────────────────────┐
  │           MAIN MENU         │
  ├─────────────────────────────┤
  │  1. Create Account          │
  │  2. Deposit                 │
  │  3. Withdraw                │
  │  4. Transfer Funds          │
  │  5. Check Balance           │
  │  6. Transaction History     │
  │  7. View All Accounts       │
  │  8. View All Transactions   │
  │  9. Delete Account          │
  │ 10. System Summary          │
  │  0. Exit                    │
  └─────────────────────────────┘
```

### Example — Creating an account

```
  ── CREATE NEW ACCOUNT ──

  Full Name     : Rahul Sharma
  Email Address : rahul@example.com
  Initial Deposit (₹) [0 to skip]: 5000

  ✔ Account created successfully!
  Account [ID: 1 | Name: Rahul Sharma | Email: rahul@example.com | Balance: ₹5000.00 | Joined: 2026-04-03]
```

### Example — Transferring funds

```
  ── TRANSFER FUNDS ──

  From Account ID : 1
  To Account ID   : 2
  Amount (₹)      : 1500

  ✔ ₹1500.00 transferred from Acc#1 to Acc#2 successfully.
  From balance: ₹3500.00
  To   balance: ₹2500.00
```

---

## 🏗 Architecture

The project follows a **3-layer architecture**:

```
┌─────────────────────────────────────────────┐
│               ConsoleUI (ui/)               │  ← Handles input/output only
├─────────────────────────────────────────────┤
│           BankingService (service/)         │  ← Business logic, validation, transactions
├─────────────────────────────────────────────┤
│        AccountDAO / TransactionDAO (dao/)   │  ← Database access only
├─────────────────────────────────────────────┤
│                MySQL Database               │
└─────────────────────────────────────────────┘
```

Each layer only talks to the layer directly below it. The UI never touches the database; the DAO never contains business rules.

---

## 🔑 Key Design Decisions

**Atomic transactions** — `deposit`, `withdraw`, and `transfer` in `BankingService` all open a single `Connection`, set `autoCommit(false)`, perform all DB operations, then either `commit()` or `rollback()`. This guarantees no partial updates (e.g. a transfer can't debit the sender without crediting the receiver).

**Connection-passing DAO overloads** — `AccountDAO` and `TransactionDAO` each expose overloaded methods that accept an existing `Connection`. This lets `BankingService` share one connection across multiple DAO calls within the same atomic operation.

**Custom exception hierarchy** — Instead of printing error strings and returning `false`, service methods throw typed exceptions (`InsufficientBalanceException`, `AccountNotFoundException`, etc.). The UI catches these and displays clean messages. This makes the service layer reusable and testable.

**Externalized DB config** — Credentials live in `db.properties`, not hardcoded in source. This is safe to commit if the file is in `.gitignore`, and makes environment changes (dev → prod) trivial.

---

## 🔮 Future Improvements

- [ ] Add a **PIN/password** field to accounts for basic authentication
- [ ] Introduce **account types** (Savings, Current) with different rules
- [ ] Add **pagination** for transaction history on large datasets
- [ ] Replace raw JDBC with **Spring JDBC Template** or **Hibernate**
- [ ] Add a **REST API layer** using Spring Boot for a web/mobile frontend
- [ ] Write **JUnit tests** for the service layer using an in-memory H2 database
- [ ] Add **transaction status** field (PENDING, SUCCESS, FAILED) for audit trails
- [ ] Implement **connection pooling** with HikariCP for production use

---

## 👨‍💻 Author

**Sujal Mhetre**
- GitHub: [@SujalMhetre](https://github.com/SujalMhetre)
- LinkedIn: [linkedin.com/in/SujalMhetre](https://www.linkedin.com/in/sujal-mhetre-5aa2b62a5/)

---