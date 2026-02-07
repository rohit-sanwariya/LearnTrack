package org.rohit.airtribe.learntask;

import org.rohit.airtribe.learntask.service.StudentService;
import org.rohit.airtribe.learntask.service.CourseService;
import org.rohit.airtribe.learntask.service.EnrollmentService;
import org.rohit.airtribe.learntask.entity.actors.student.Student;
import org.rohit.airtribe.learntask.entity.course.Course;
import org.rohit.airtribe.learntask.entity.enrollment.Enrollment;
import java.util.List;

public class Main {
    static void main() {
        System.out.println("=== LearnTrack - Testing Service Classes ===\n");

        // Initialize services
        StudentService studentService = new StudentService();
        CourseService courseService = new CourseService();
        EnrollmentService enrollmentService = new EnrollmentService();

        // Test 1: Display initial data
        System.out.println("1. INITIAL DATA:");
        System.out.println("Total Students: " + studentService.getStudentCount());
        System.out.println("Total Courses: " + courseService.getCourseCount());
        System.out.println("Total Enrollments: " + enrollmentService.getEnrollmentCount());

        // Test 2: Add new entities using services
        System.out.println("\n2. ADDING NEW ENTRIES:");

        // Add new student (method overloading example)
        Student newStudent = studentService.addStudent("Sachin", "Tendulkar", "sachin@example.com", "BatchD");
        System.out.println("Added Student: " + newStudent.getDisplayName());

        // Add new course
        Course newCourse = courseService.addCourse("Database Management", "SQL and NoSQL databases", 10);
        System.out.println("Added Course: " + newCourse.getCourseName());

        // Add new enrollment
        Enrollment newEnrollment = enrollmentService.enrollStudent(
                newStudent.getId(),
                newCourse.getId(),
                "2024-01-20"
        );
        System.out.println("Added Enrollment ID: " + newEnrollment.getId());

        // Test 3: Display all data
        System.out.println("\n3. ALL STUDENTS:");
        List<Student> allStudents = studentService.getAllStudents();
        for (Student s : allStudents) {
            System.out.println("  - " + s);
        }

        System.out.println("\n4. ALL COURSES:");
        List<Course> allCourses = courseService.getAllCourses();
        for (Course c : allCourses) {
            System.out.println("  - " + c);
        }

        System.out.println("\n5. ALL ENROLLMENTS:");
        List<Enrollment> allEnrollments = enrollmentService.getAllEnrollments();
        for (Enrollment e : allEnrollments) {
            System.out.println("  - " + e);
        }

        // Test 4: Search functionality
        System.out.println("\n6. SEARCH FUNCTIONALITY:");
        List<Student> searchResults = studentService.searchStudentsByName("rohit");
        System.out.println("Search for 'rohit': " + searchResults.size() + " result(s)");
        for (Student s : searchResults) {
            System.out.println("  - Found: " + s.getDisplayName());
        }

        // Test 5: Update operations
        System.out.println("\n7. UPDATE OPERATIONS:");

        // Deactivate a student
        boolean deactivated = studentService.deactivateStudent(1000);
        System.out.println("Student 1000 deactivated: " + deactivated);

        // Complete an enrollment
        boolean completed = enrollmentService.completeEnrollment(3000);
        System.out.println("Enrollment 3000 completed: " + completed);

        // Test 6: Get active entities only
        System.out.println("\n8. ACTIVE ENTITIES ONLY:");
        System.out.println("Active Students: " + studentService.getActiveStudents().size());
        System.out.println("Active Courses: " + courseService.getActiveCourses().size());
        System.out.println("Active Enrollments: " + enrollmentService.getActiveEnrollmentCount());

        // Test 7: Get by ID
        System.out.println("\n9. GET BY ID:");
        Student studentById = studentService.getStudentById(1001);
        System.out.println("Student 1001: " + (studentById != null ? studentById.getDisplayName() : "Not found"));

        Course courseById = courseService.getCourseById(2001);
        System.out.println("Course 2001: " + (courseById != null ? courseById.getCourseName() : "Not found"));

        // Test 8: Get enrollments for a student
        System.out.println("\n10. ENROLLMENTS FOR STUDENT 1001:");
        List<Enrollment> studentEnrollments = enrollmentService.getEnrollmentsByStudentId(1001);
        for (Enrollment e : studentEnrollments) {
            System.out.println("  - Course ID: " + e.getCourseId() + ", Status: " + e.getStatus());
        }

        System.out.println("\n=== Service classes tested successfully! ===");
    }
}