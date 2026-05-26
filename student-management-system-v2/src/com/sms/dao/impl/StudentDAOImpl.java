package com.sms.dao.impl;

import com.sms.dao.StudentDAO;
import com.sms.model.Student;
import com.sms.util.DBConnection;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class StudentDaoImpl implements StudentDAO {

    @Override
    public boolean addStudent(Student student) {
        String query = "INSERT INTO students (name,email,age,course,cgpa) VALUES(?,?,?,?,?)";

        try (Connection connection = DBConnection.getConnection();

             PreparedStatement ps = connection.prepareStatement(query);) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getCourse());
            ps.setDouble(5, student.getCgpa());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public Student getStudentById(int studentId) {
        String query = "SELECT * FROM students WHERE studentId = ?";
        try (
                Connection connection = DBConnection.getConnection();

                PreparedStatement ps = connection.prepareStatement(query);
        ) {
            ps.setInt(1, studentId);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                Student student = new Student(
                        rs.getInt("studentId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getDouble("cgpa"),
                        rs.getTimestamp("admittedAt").toLocalDateTime()
                );
                return student;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Student> getAllStudents() {

        List<Student> students = new ArrayList<>();

        String query = "SELECT * FROM students";

        try (
                Connection connection = DBConnection.getConnection();

                PreparedStatement ps = connection.prepareStatement(query);

                ResultSet rs = ps.executeQuery();
        ) {

            while (rs.next()) {
                Student student = new Student(
                        rs.getInt("studentId"),
                        rs.getString("name"),
                        rs.getString("email"),
                        rs.getInt("age"),
                        rs.getString("course"),
                        rs.getDouble("cgpa"),
                        rs.getTimestamp("admittedAt").toLocalDateTime()
                );
                students.add(student);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return students;
    }

    @Override
    public boolean updateStudent(Student student) {

        String query = "UPDATE students SET name =?, email = ?, age = ?, course = ?, cgpa = ? WHERE studentId = ?";

        try (
                Connection connection = DBConnection.getConnection();

                PreparedStatement ps = connection.prepareStatement(query);
        ) {
            ps.setString(1, student.getName());
            ps.setString(2, student.getEmail());
            ps.setInt(3, student.getAge());
            ps.setString(4, student.getCourse());
            ps.setDouble(5, student.getCgpa());
            ps.setInt(6, student.getStudentId());

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public boolean deleteStudent(int studentId) {

        String query = "DELETE FROM students WHERE studentId = ?";

        try (
                Connection connection = DBConnection.getConnection();

                PreparedStatement ps = connection.prepareStatement(query);

        ) {
            ps.setInt(1, studentId);

            int rowsAffected = ps.executeUpdate();

            return rowsAffected > 0;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
}
