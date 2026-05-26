package com.sms.service;

import com.sms.model.Student;

import java.util.List;

public interface StudentService {

    boolean addStudent(Student student);

    Student getStudentById(int studentId);

    List<Student> getAllStudents();

    boolean updateStudent(Student student);

    boolean deleteStudent(int studentId);
}
