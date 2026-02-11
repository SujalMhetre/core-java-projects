package com.sms.service.impl;

import java.util.List;
import com.sms.dao.StudentDAO;
import com.sms.dao.impl.StudentDAOImpl;
import com.sms.model.Student;
import com.sms.service.StudentService;

public class StudentServiceImpl implements StudentService {

    private StudentDAO studentDAO;

    public StudentServiceImpl() {
        this.studentDAO = new StudentDAOImpl();
    }

    @Override
    public boolean addStudent(Student student) {
        if (!isValidStudent(student)) {
            return false;
        }
        Student existingStudent = studentDAO.getStudentById(student.getStudentId());

        if (existingStudent != null) {
            return false;
        }

        return studentDAO.addStudent(student);
    }

    @Override
    public Student getStudentById(int studentId) {
        if (studentId <= 0) {
            return null;
        }
        return studentDAO.getStudentById(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return studentDAO.getAllStudents();
    }

    @Override
    public boolean updateStudent(Student student) {
        if (!isValidStudent(student)) {
            return false;
        }
        return studentDAO.updateStudent(student);
    }

    @Override
    public boolean deleteStudent(int studentId) {
        if (studentId <= 0) {
            return false;
        }
        return studentDAO.deleteStudent(studentId);
    }

    private boolean isValidStudent(Student student) {
        if (student == null) {
            return false;
        }
        if (student.getStudentId() <= 0) {
            return false;
        }
        if (student.getName() == null || student.getName().trim().isEmpty()) {
            return false;
        }
        if (student.getEmail() == null || !student.getEmail().contains("@")) {
            return false;
        }
        if (student.getAge() <= 0) {
            return false;
        }
        if (student.getCourse() == null || student.getCourse().trim().isEmpty()) {
            return false;
        }

        return true;
    }
}
