package com.sms.model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Student {

	private int studentId;
	private String name;
	private String email;
	private int age;
	private String course;
	private double cgpa;
	private LocalDateTime admittedAt;

	// Constructors

	public Student() {
	}

	public Student(String name, String email, int age, String course, double cgpa) {
		this.name = name;
		this.email = email;
		this.age = age;
		this.course = course;
		this.cgpa = cgpa;
	}

	public Student(int studentId, String name, String email, int age, String course, double cgpa,
			LocalDateTime admittedAt) {
		this.studentId = studentId;
		this.name = name;
		this.email = email;
		this.age = age;
		this.course = course;
		this.cgpa = cgpa;
		this.admittedAt = admittedAt;
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

	public double getCgpa() {
		return cgpa;
	}

	public LocalDateTime getAdmittedAt() {
		return admittedAt;
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

	public void setCgpa(double cgpa) {
		this.cgpa = cgpa;
	}

	// ToString

	@Override
	public String toString() {

		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy hh:mm a");

		return """
				==========================================

				StudentId    : %d
				Name         : %s
				Email        : %s
				Age          : %d
				Course       : %s
				CGPA         : %.2f
				Admitted At  : %s

				==========================================
				""".formatted(studentId, name, email, age, course, cgpa, admittedAt.format(formatter));
	}
}
