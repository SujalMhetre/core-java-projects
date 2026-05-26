# 🎓 Student Management System — V1

A console-based **Student Management System** built with **Core Java**, demonstrating OOP principles, layered architecture, and in-memory data management using Java Collections.

> **Version 1** — No database required. Designed for learning and portfolio purposes.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [How to Run](#how-to-run)
- [Architecture](#architecture)
- [Future Plans (V2)](#future-plans-v2)

---

## 🔍 Overview

This project showcases fundamental Java backend development skills without any external dependencies. Everything runs in-memory using core Java — no frameworks, no database, no setup friction.

**Concepts demonstrated:**
- Object-Oriented Programming (Encapsulation, Abstraction, Interfaces)
- Layered architecture — UI → Service → DAO → Model
- Java Collections (`HashMap`) for in-memory CRUD
- Input validation and business rule enforcement
- Clean console-based user interface

---

## ✨ Features

| # | Feature | Description |
|---|---|---|
| 1 | **Add Student** | Stores ID, Name, Email, Age, and Course with full input validation |
| 2 | **View by ID** | Fetches a single student record by unique ID |
| 3 | **View All Students** | Lists every student currently in the system |
| 4 | **Update Student** | Edits existing student details with re-validation |
| 5 | **Delete Student** | Removes a student record by ID |
| 6 | **Exit** | Terminates the application safely |

**Validation rules enforced:**
- Student ID must be unique
- Name and Course cannot be empty
- Email must contain `@`
- Age must be greater than 0

---

## 🛠️ Tech Stack

| Technology | Usage |
|---|---|
| Java SE 8+ | Core language |
| `HashMap<Integer, Student>` | In-memory data storage |
| Java Collections Framework | Data management |
| Console / Scanner | User interface |

**IDE:** Eclipse / VS Code  
**Build:** No build tool required — compile and run directly

---

## 📁 Project Structure

```
student-management-system-v1/
├── src/
│   └── com/sms/
│       ├── model/
│       │   └── Student.java               # Student entity (fields + getters/setters)
│       ├── dao/
│       │   ├── StudentDAO.java            # DAO interface (CRUD contracts)
│       │   └── impl/
│       │       └── StudentDAOImpl.java    # HashMap-backed implementation
│       ├── service/
│       │   ├── StudentService.java        # Service interface (business logic contracts)
│       │   └── impl/
│       │       └── StudentServiceImpl.java # Validation + delegates to DAO
│       └── ui/
│           └── MainApp.java               # Console menu and user interaction
├── .gitignore
└── README.md
```

---

## 🚀 How to Run

### Option 1 — Command Line

```bash
# 1. Clone the repository
git clone https://github.com/SujalMhetre/core-java-projects.git
cd core-java-projects/student-management-system-v1

# 2. Compile
javac -d out src/com/sms/**/*.java src/com/sms/dao/impl/*.java src/com/sms/service/impl/*.java src/com/sms/ui/*.java

# 3. Run
java -cp out com.sms.ui.MainApp
```

### Option 2 — IDE (Eclipse / VS Code)

1. Open the project folder in your IDE
2. Navigate to `src/com/sms/ui/MainApp.java`
3. Run as a Java Application

No external JARs or configuration needed.

---

## 🏗️ Architecture

The project follows a strict **4-layer architecture** — each layer has one responsibility and only communicates with the layer directly below it.

```
┌──────────────────────────────────────┐
│         MainApp.java  (ui/)          │  ← Console I/O only
├──────────────────────────────────────┤
│     StudentServiceImpl (service/)    │  ← Validation & business rules
├──────────────────────────────────────┤
│       StudentDAOImpl  (dao/)         │  ← HashMap CRUD operations
├──────────────────────────────────────┤
│         Student.java  (model/)       │  ← Plain data object
└──────────────────────────────────────┘
```

This mirrors the architecture used in real enterprise Java applications (Spring MVC, Jakarta EE), making V2 — a database-backed version — a natural next step.

---

## 🔮 Future Plans (V2)

- [ ] Replace `HashMap` with **MySQL** database via JDBC
- [ ] Add **custom exceptions** (`StudentNotFoundException`, `DuplicateStudentException`)
- [ ] Introduce **Maven** for dependency and build management
- [ ] Add **search and filter** — by name, course, or age range
- [ ] Write **JUnit tests** for the service layer
- [ ] Upgrade to a **Spring Boot REST API** in V3

---

## 👨‍💻 Author

**Sujal Mhetre**
- GitHub: [@SujalMhetre](https://github.com/SujalMhetre)

---