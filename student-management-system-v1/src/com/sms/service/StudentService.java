package com.sms.service;

import java.util.List;

import com.sms.model.Student;

public interface StudentService {

    boolean addStudent(Student student);

    Student getStudentById(int studentId);

    List<Student> getAllStudents();

    boolean updateStudent(Student student);

    boolean deleteStudent(int studentId);
}
