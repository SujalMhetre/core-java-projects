package com.sms.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import com.sms.dao.StudentDAO;
import com.sms.model.Student;

public class StudentDAOImpl implements StudentDAO {

    private Map<Integer, Student> studentStore = new HashMap<>();

    @Override
    public boolean addStudent(Student student) {
        if (studentStore.containsKey(student.getStudentId())) {
            return false;
        }
        studentStore.put(student.getStudentId(), student);
        return true;
    }

    @Override
    public Student getStudentById(int studentId) {
        return studentStore.get(studentId);
    }

    @Override
    public List<Student> getAllStudents() {
        return new ArrayList<>(studentStore.values());
    }

    @Override
    public boolean updateStudent(Student student) {
        if (!studentStore.containsKey(student.getStudentId())) {
            return false;
        }
        studentStore.put(student.getStudentId(), student);
        return true;
    }

    @Override
    public boolean deleteStudent(int studentId) {
        if (!studentStore.containsKey(studentId)) {
            return false;
        }
        studentStore.remove(studentId);
        return true;
    }

}
