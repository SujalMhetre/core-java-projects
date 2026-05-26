package com.sms.ui;

import com.sms.model.Student;
import com.sms.service.StudentService;
import com.sms.service.impl.StudentServiceImpl;
import java.util.List;
import java.util.Scanner;

public class MainApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        StudentService studentService = new StudentServiceImpl();

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("==================STUDENT MANAGEMENT SYSTEM=====================");
            System.out.println("1: Add Student");
            System.out.println("2: View Student By ID");
            System.out.println("3: View All Students");
            System.out.println("4: Update Student");
            System.out.println("5: Delete Student");
            System.out.println("6: Exit");

            System.out.print("Choose a Option: ");
            int choice = scanner.nextInt();

            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter email: ");
                    String email = scanner.nextLine();

                    System.out.print("Enter age: ");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter course: ");
                    String course = scanner.nextLine();

                    System.out.print("Enter CGPA: ");
                    double cgpa = scanner.nextDouble();
                    scanner.nextLine();

                    Student student = new Student(name, email, age, course, cgpa);

                    boolean added = studentService.addStudent(student);

                    System.out.println(added ? "Successfully Added Student" : "Failed to Add Student");
                    break;

                case 2:
                    System.out.print("Enter StudentID: ");
                    int getId = scanner.nextInt();

                    Student temp = studentService.getStudentById(getId);

                    if (temp == null) {
                        System.out.println("No Student Found");
                    } else {
                        System.out.println(temp);
                    }
                    break;

                case 3:
                    List<Student> studentList = studentService.getAllStudents();

                    if (studentList.isEmpty()) {
                        System.out.println("No Students Available");
                    } else {
                        for (Student values : studentList) {
                            System.out.println(values);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter StudentId: ");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Name: ");
                    String newName = scanner.nextLine();

                    System.out.print("Enter Email: ");
                    String newEmail = scanner.nextLine();

                    System.out.print("Enter Age: ");
                    int newAge = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Course: ");
                    String newCourse = scanner.nextLine();

                    System.out.print("Enter CGPA: ");
                    double newCgpa = scanner.nextDouble();

                    Student updateStudent = new Student(newName, newEmail, newAge, newCourse, newCgpa);

                    boolean updated = studentService.updateStudent(updateStudent);

                    System.out.println(updated ? "Successfully updated student" : "Failed updating student");

                    break;

                case 5:
                    System.out.print("Enter StudentId: ");
                    int deleteStudentId = scanner.nextInt();

                    boolean deleted = studentService.deleteStudent(deleteStudentId);

                    System.out.println(deleted ? "Successfully deleted student" : "Failed deleting student");
                    break;

                case 6:
                    System.out.println("Exiting Application!!");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice!!");
            }

        }
        scanner.close();
    }
}
