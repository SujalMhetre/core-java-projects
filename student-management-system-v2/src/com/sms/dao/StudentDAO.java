package com.sms.dao;

import java.util.List;

import com.sms.model.Student;

public interface StudentDAO {

    boolean addStudent(Student student);

    Student getStudentById(int studentId);

    List<Student> getAllStudents();

    boolean updateStudent(Student student);

    boolean deleteStudent(int studentId);
}
