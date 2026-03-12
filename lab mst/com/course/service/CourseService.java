package com.course.service;

import com.course.model.Course;
import com.course.model.Student;
import com.course.exception.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courses = new ArrayList<>();
    private static final String FILE_NAME = "courses.txt";

    public void addCourse(Course c) {
        courses.add(c);
        saveToFile();
    }

    public void enrollStudent(int courseId, Student s) throws CourseNotFoundException, CourseFullException, DuplicateEnrollmentException {
        Course course = null;
        for (Course c : courses) {
            if (c.getCourseId() == courseId) {
                course = c;
                break;
            }
        }

        if (course == null) {
            throw new CourseNotFoundException("Course with ID " + courseId + " not found");
        }

        for (Student enrolled : course.getEnrolledStudents()) {
            if (enrolled.getStudentId() == s.getStudentId()) {
                throw new DuplicateEnrollmentException("Student " + s.getStudentName() + " is already enrolled in " + course.getCourseName());
            }
        }

        if (course.getEnrolledStudents().size() >= course.getMaxSeats()) {
            throw new CourseFullException("Course " + course.getCourseName() + " is full");
        }

        course.getEnrolledStudents().add(s);
        saveToFile();
    }

    public void viewCourses() {
        if (courses.isEmpty()) {
            System.out.println("No courses available");
            return;
        }
        for (Course c : courses) {
            c.display();
            System.out.println("-------------------");
        }
    }

    private void saveToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_NAME))) {
            for (Course c : courses) {
                writer.write("COURSE:" + c.getCourseId() + "," + c.getCourseName() + "," + c.getMaxSeats());
                writer.newLine();
                for (Student s : c.getEnrolledStudents()) {
                    writer.write("STUDENT:" + s.getStudentId() + "," + s.getStudentName());
                    writer.newLine();
                }
            }
        } catch (IOException e) {
            System.out.println("Error saving to file: " + e.getMessage());
        }
    }

    public void loadFromFile() {
        File file = new File(FILE_NAME);
        if (!file.exists()) return;

        try (BufferedReader reader = new BufferedReader(new FileReader(FILE_NAME))) {
            String line;
            Course currentCourse = null;
            courses.clear();

            while ((line = reader.readLine()) != null) {
                if (line.startsWith("COURSE:")) {
                    String[] parts = line.substring(7).split(",");
                    currentCourse = new Course(Integer.parseInt(parts[0]), parts[1], Integer.parseInt(parts[2]));
                    courses.add(currentCourse);
                } else if (line.startsWith("STUDENT:") && currentCourse != null) {
                    String[] parts = line.substring(8).split(",");
                    currentCourse.getEnrolledStudents().add(new Student(Integer.parseInt(parts[0]), parts[1]));
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading from file: " + e.getMessage());
        }
    }
}
