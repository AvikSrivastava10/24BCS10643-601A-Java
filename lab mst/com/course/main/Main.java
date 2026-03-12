package com.course.main;

import com.course.model.*;
import com.course.service.CourseService;
import com.course.exception.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        CourseService service = new CourseService();
        service.loadFromFile();
        Scanner sc = new Scanner(System.in);

        while (true) {
            System.out.println("\n=== Online Course Enrollment System ===");
            System.out.println("1. Add Course");
            System.out.println("2. Enroll Student");
            System.out.println("3. View Courses");
            System.out.println("4. Exit");
            System.out.print("Enter choice: ");

            int choice = sc.nextInt();
            sc.nextLine();

            try {
                switch (choice) {
                    case 1:
                        System.out.print("Enter Course ID: ");
                        int courseId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Course Name: ");
                        String courseName = sc.nextLine();
                        System.out.print("Enter Max Seats: ");
                        int maxSeats = sc.nextInt();
                        service.addCourse(new Course(courseId, courseName, maxSeats));
                        System.out.println("Course added successfully!");
                        break;

                    case 2:
                        System.out.print("Enter Course ID: ");
                        int cId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Student ID: ");
                        int studentId = sc.nextInt();
                        sc.nextLine();
                        System.out.print("Enter Student Name: ");
                        String studentName = sc.nextLine();
                        service.enrollStudent(cId, new Student(studentId, studentName));
                        System.out.println("Student enrolled successfully!");
                        break;

                    case 3:
                        service.viewCourses();
                        break;

                    case 4:
                        System.out.println("Exiting...");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice!");
                }
            } catch (CourseFullException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (CourseNotFoundException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (DuplicateEnrollmentException e) {
                System.out.println("Error: " + e.getMessage());
            } catch (Exception e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}
