package org.rohit.airtribe.learntask;

import org.rohit.airtribe.learntask.entity.actors.person.Person;
import org.rohit.airtribe.learntask.entity.actors.student.Student;
import org.rohit.airtribe.learntask.entity.course.Course;
import org.rohit.airtribe.learntask.entity.enrollment.Enrollment;
import org.rohit.airtribe.learntask.entity.enrollment.EntrollmentStatus;
import org.rohit.airtribe.learntask.util.IDGenerator;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== LearnTrack - Student & Course Management System ===\n");

        // Test IDGenerator (static utility)
        System.out.println("1. Testing IDGenerator (Static Utility):");
        System.out.println("Next Student ID: " + IDGenerator.getNextStudentId());
        System.out.println("Next Course ID: " + IDGenerator.getNextCourseId());
        System.out.println("Next Enrollment ID: " + IDGenerator.getNextEnrollmentId());

        // Test Person and Student (Inheritance)
        System.out.println("\n2. Testing Inheritance (Person → Student):");
        Person person = new Person(1, "John", "Doe", "john@example.com");
        System.out.println("Person: " + person);

        Student student = new Student(
                IDGenerator.getNextStudentId(),
                "Alice",
                "Smith",
                "alice@example.com",
                "Spring2024"
        );
        System.out.println("Student: " + student);
        System.out.println("Display Name (Polymorphism): " + student.getDisplayName());

        // Test Course
        System.out.println("\n3. Testing Course:");
        Course course = new Course(
                IDGenerator.getNextCourseId(),
                "Java Fundamentals",
                "Learn core Java programming",
                8
        );
        System.out.println("Course: " + course);

        // Test Enrollment
        System.out.println("\n4. Testing Enrollment:");
        Enrollment enrollment = new Enrollment(
                IDGenerator.getNextEnrollmentId(),
                student.getId(),
                course.getId(),
                "2024-01-15"
        );
        System.out.println("Enrollment: " + enrollment);

        // Update enrollment status
        enrollment.setStatus(EntrollmentStatus.COMPLETED);
        System.out.println("Updated Enrollment: " + enrollment);

        // Test encapsulation
        System.out.println("\n5. Testing Encapsulation:");
        student.setActive(false);
        course.setActive(false);
        System.out.println("Student active status: " + student.isActive());
        System.out.println("Course active status: " + course.isActive());

        System.out.println("\n=== All entity tests completed successfully! ===");
    }
}