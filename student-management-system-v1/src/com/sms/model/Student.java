package com.sms.model;

import java.time.LocalDateTime;

public class Student {

	private int studentId;
	private String name;
	private String email;
	private int age;
	private String course;
	private LocalDateTime createdAt;

	// Constructor
	public Student(int studentId, String name, String email, int age, String course) {
		super();
		this.studentId = studentId;
		this.name = name;
		this.email = email;
		this.age = age;
		this.course = course;
		this.createdAt = LocalDateTime.now();
	}

	// Getters
	public int getStudentId() {
		return studentId;
	}

	public String getName() {
		return name;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public String getCourse() {
		return course;
	}

	public LocalDateTime getCreatedAt() {
		return createdAt;
	}

	// Setters
	public void setName(String name) {
		this.name = name;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setCourse(String course) {
		this.course = course;
	}

	// To-String()
	@Override
	public String toString() {
		return "Student [studentId=" + studentId + ", name=" + name + ", email=" + email + ", age=" + age + ", course="
				+ course + ", createdAt=" + createdAt + "]";
	}
}
