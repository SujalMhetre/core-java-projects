# Student Management System V1

**A console-based Java application to manage student records using Core Java, OOP principles, and collections.**

---

## 🔹 Overview

This project is a **Student Management System** built using **Core Java**.  
It demonstrates:

- Object-Oriented Programming (OOP) concepts
- Layered architecture (UI → Service → DAO → Model)
- Java Collections (`HashMap`) for in-memory data storage
- Input validation and basic business rules
- Console-based user interface

This is **Version 1**, implemented without a database, designed purely for **learning and portfolio purposes**.

---

## 🔹 Features

1. **Add Student**  
   - Stores student information: ID, Name, Email, Age, Course  
   - Validates inputs (ID uniqueness, non-empty name, valid email, age > 0)

2. **View Student By ID**  
   - Fetches a single student by their unique ID

3. **View All Students**  
   - Lists all students stored in the system

4. **Update Student**  
   - Updates existing student information with input validation

5. **Delete Student**  
   - Removes student by ID

6. **Exit Application**  
   - Terminates the program safely

---

## 🔹 Technology Stack

- **Language:** Java SE 8+  
- **Build:** Console / Command-line  
- **IDE Tested:** Eclipse & VS Code  
- **Data Storage:** `HashMap<Integer, Student>` (in-memory)  

---

## 🔹 Project Structure

- `student-management-system-v1/`<br>
&nbsp;&nbsp;&nbsp;&nbsp;• `src/`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• `com/sms/`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• `model/` — `Student.java`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• `dao/` — `StudentDAO.java`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• `dao/impl/` — `StudentDAOImpl.java`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• `service/` — `StudentService.java`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• `service/impl/` — `StudentServiceImpl.java`  
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;• `ui/` — `MainApp.java`  
&nbsp;&nbsp;&nbsp;&nbsp;• `README.md`  
&nbsp;&nbsp;&nbsp;&nbsp;• `.gitignore`  


---

## 🔹 How to Run

1. Clone the repository:

```bash
git clone <https://github.com/SujalMhetre/core-java-projects.git>
