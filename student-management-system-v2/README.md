# Student Management System — V2 (JDBC + MySQL)

> Console-based Java application upgraded from V1 (in-memory HashMap) to a fully persistent JDBC + MySQL storage system.

![Java](https://img.shields.io/badge/Java-17-orange?style=flat-square&logo=java)
![MySQL](https://img.shields.io/badge/MySQL-8.0-blue?style=flat-square&logo=mysql)
![JDBC](https://img.shields.io/badge/JDBC-Driver-green?style=flat-square)
![Status](https://img.shields.io/badge/Status-Complete-brightgreen?style=flat-square)

---

## Overview

**V1** was a console-based student management system that stored all data in a `HashMap` at runtime — data was lost every time the program exited.

**V2** upgrades the entire persistence layer to use **JDBC + MySQL**, replacing the in-memory store with a real relational database. The console interface remains the same, but all student records are now permanently saved, updated, and retrieved from a MySQL database using structured SQL queries through the DAO pattern.

---

## What's New in V2

- Replaced `HashMap` in-memory storage with **MySQL relational database**
- Introduced **JDBC** for database connectivity and query execution
- Implemented the **DAO (Data Access Object)** design pattern to separate data logic from business logic
- Added a dedicated **`DBConnection` utility class** for managing database connections
- All CRUD operations now execute real **SQL queries** (INSERT, SELECT, UPDATE, DELETE)
- Data **persists across sessions** — no data loss on program exit
- Added `email` field as a `UNIQUE` constraint to prevent duplicate records
- Introduced `AUTO_INCREMENT` primary key for `studentId`

---

## Features

| Operation | Description |
|-----------|-------------|
| Add Student | Insert a new student record into the database |
| View All Students | Fetch and display all records from the `students` table |
| Search by ID | Retrieve a single student record by `studentId` |
| Update Student | Modify name, email, age, or course for an existing record |
| Delete Student | Remove a student record permanently from the database |

---

## Tech Stack

| Layer | Technology |
|-------|------------|
| Language | Java 17 |
| Database Connectivity | JDBC (Java Database Connectivity) |
| Database | MySQL 8.0 |
| Query Language | SQL |
| IDE | IntelliJ IDEA / VS Code |
| Build | Manual (no build tool) |

---

## Database Setup

**Step 1 — Create the database**

```sql
CREATE DATABASE IF NOT EXISTS student_management_system;
USE student_management_system;
```

**Step 2 — Create the students table**

```sql
CREATE TABLE IF NOT EXISTS students (
    studentId  INT PRIMARY KEY AUTO_INCREMENT,
    name       VARCHAR(100) NOT NULL,
    email      VARCHAR(150) NOT NULL UNIQUE,
    age        INT NOT NULL,
    course     VARCHAR(100) NOT NULL,
    cgpa       DOUBLE NOT NULL,
    admittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

**Step 3 — Verify**

```sql
DESCRIBE students;
```

---

## Project Structure

```
StudentManagementSystemV2/
├── src/
│   ├── main/
│   │   ├── Main.java                  # Entry point — console menu loop
│   │   ├── model/
│   │   │   └── Student.java           # Student entity / POJO
│   │   ├── dao/
│   │   │   ├── StudentDAO.java        # DAO interface — CRUD contracts
│   │   │   └── StudentDAOImpl.java    # JDBC implementation of DAO
│   │   ├── service/
│   │   │   └── StudentService.java    # Business logic layer
│   │   └── util/
│   │       └── DBConnection.java      # JDBC connection utility
├── lib/
│   └── mysql-connector-j-8.x.x.jar   # MySQL JDBC driver
├── sql/
│   └── schema.sql                     # Database and table creation script
└── README.md
```

---

## Architecture

```
Console UI  (Main.java)
     │
     ▼
Service Layer  (StudentService.java)
     │         Business logic, validation
     ▼
DAO Layer  (StudentDAOImpl.java)
     │     SQL queries via JDBC
     ▼
Database  (MySQL — students table)
```

---

## How to Run

### Prerequisites

- Java 17+ installed
- MySQL 8.0 installed and running
- MySQL JDBC Driver (`mysql-connector-j`) downloaded and placed in `/lib`

### Step 1 — Clone the repository

```bash
git clone https://github.com/SujalMhetre/StudentManagementSystemV2.git
cd StudentManagementSystemV2
```

### Step 2 — Set up the database

```bash
mysql -u root -p < sql/schema.sql
```

Or open MySQL Workbench and run the contents of `sql/schema.sql` manually.

### Step 3 — Configure DB credentials

Open `src/main/util/DBConnection.java` and update:

```java
private static final String URL  = "jdbc:mysql://localhost:3306/student_management_system";
private static final String USER = "your_mysql_username";
private static final String PASS = "your_mysql_password";
```

### Step 4 — Compile

```bash
javac -cp lib/mysql-connector-j-8.x.x.jar -d out src/main/**/*.java src/main/Main.java
```

### Step 5 — Run

```bash
java -cp out:lib/mysql-connector-j-8.x.x.jar main.Main
```

> On Windows, replace `:` with `;` in the classpath.

---

## Key Improvements Over V1

| Aspect | V1 (HashMap) | V2 (JDBC + MySQL) |
|--------|--------------|-------------------|
| Storage | In-memory `HashMap` | MySQL relational database |
| Persistence | Lost on exit | Permanent across sessions |
| Data integrity | None | `UNIQUE` email, `NOT NULL` constraints |
| ID generation | Manual / random | `AUTO_INCREMENT` primary key |
| Architecture | Single-class logic | Layered: Service + DAO + Util |
| Scalability | Limited to JVM heap | Limited only by DB capacity |
| Query capability | Linear search | SQL `WHERE`, `ORDER BY`, indexed queries |

---

## Future Improvements

- [ ] Migrate to **Spring Boot** with Spring Data JPA
- [ ] Add **Hibernate ORM** to replace raw JDBC queries
- [ ] Build a **REST API layer** with Spring MVC
- [ ] Add structured **exception handling** and custom exceptions
- [ ] Integrate **SLF4J + Logback** for proper logging
- [ ] Add **input validation** with meaningful error messages
- [ ] Write **unit tests** with JUnit 5 and Mockito
- [ ] Connect a **React.js frontend** (roadmap to V3 full stack)

---

## Author

**Sujal Mhetre**
Full Stack Developer (Fresher) · Pune, Maharashtra

- GitHub: [@SujalMhetre](https://github.com/SujalMhetre)
- LinkedIn: [linkedin.com/in/sujalmhetre](https://linkedin.com/in/sujalmhetre)

---

> This project is part of a structured Java Full Stack learning path: Core Java → JDBC + MySQL → Spring Boot → React.js