CREATE DATABASE IF NOT EXISTS student_management_system;

USE student_management_system;

CREATE TABLE students(
studentId INT AUTO_INCREMENT PRIMARY KEY,
name VARCHAR(50) NOT NULL,
email VARCHAR(50) NOT NULL UNIQUE,
age INT,
course VARCHAR(50),
cgpa DOUBLE,
admittedAt TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


