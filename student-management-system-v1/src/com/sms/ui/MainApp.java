package com.sms.ui;

import java.util.List;
import java.util.Scanner;
import com.sms.model.Student;
import com.sms.service.StudentService;
import com.sms.service.impl.StudentServiceImpl;

public class MainApp {

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        StudentService studentService = new StudentServiceImpl();

        boolean isRunning = true;

        while (isRunning) {
            System.out.println("=================Student Management System====================");
            System.out.println("1: Add Student");
            System.out.println("2: View Student By ID");
            System.out.println("3: View All Students");
            System.out.println("4: Update Student");
            System.out.println("5: Delete Student");
            System.out.println("6: Exit");
            System.out.print("Choose a option :");

            int choice = scanner.nextInt();
            scanner.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter Id :");
                    int id = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Name :");
                    String name = scanner.nextLine();

                    System.out.print("Enter Email :");
                    String email = scanner.nextLine();

                    System.out.print("Enter age :");
                    int age = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Course :");
                    String course = scanner.nextLine();

                    Student student = new Student(id, name, email, age, course);

                    boolean added = studentService.addStudent(student);

                    System.out.println(added ? "Successfully Added Student" : "Failed Adding Student");
                    break;

                case 2:
                    System.out.print("Enter StudentId :");
                    int getId = scanner.nextInt();
                    scanner.nextLine();

                    Student temp = studentService.getStudentById(getId);

                    if (temp == null) {
                        System.out.println("No Student Found");
                    } else {
                        System.out.println(temp);
                    }

                    break;

                case 3:
                    List<Student> students = studentService.getAllStudents();

                    if (students.isEmpty()) {
                        System.out.println("No Students Available");
                    } else {
                        for (Student values : students) {
                            System.out.println(values);
                        }
                    }
                    break;

                case 4:
                    System.out.print("Enter StudentId to Update:");
                    int updateId = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Name :");
                    String newName = scanner.nextLine();

                    System.out.print("Enter Email :");
                    String newEmail = scanner.nextLine();

                    System.out.print("Enter age :");
                    int newAge = scanner.nextInt();
                    scanner.nextLine();

                    System.out.print("Enter Course :");
                    String newCourse = scanner.nextLine();

                    Student updateStudent = new Student(updateId, newName, newEmail, newAge, newCourse);

                    boolean updated = studentService.updateStudent(updateStudent);

                    System.out.println(updated ? "Successfully Updated Student" : "Failed Updateing Student");
                    break;

                case 5:
                    System.out.print("Enter StudentId to Delete:");
                    int deleteId = scanner.nextInt();
                    scanner.nextLine();

                    boolean deleted = studentService.deleteStudent(deleteId);

                    System.out.println(deleted ? "Student Deleted Successfully" : "Failed Deleting Student");
                    break;

                case 6:
                    System.out.println("Exiting Application!");
                    isRunning = false;
                    break;

                default:
                    System.out.println("Invalid Choice !");
            }

        }

        scanner.close();

    }

}
