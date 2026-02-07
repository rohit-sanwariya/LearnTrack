package org.rohit.airtribe.learntask.service;

import org.rohit.airtribe.learntask.entity.actors.student.Student;
import org.rohit.airtribe.learntask.util.IDGenerator;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    // ArrayList to store Student objects (Collection)
    private List<Student> students;

    // Constructor
    public StudentService() {
        this.students = new ArrayList<>();
        initializeSampleData(); // Optional: add some sample data
    }

    // CRUD Operations

    // Create - Add new student
    public Student addStudent(String firstName, String lastName, String email, String batch) {
        int newId = IDGenerator.getNextStudentId();
        Student student = new Student(newId, firstName, lastName, email, batch);
        students.add(student);
        return student;
    }

    // Method overloading example (add student without email)
    public Student addStudent(String firstName, String lastName, String batch) {
        int newId = IDGenerator.getNextStudentId();
        Student student = new Student(newId, firstName, lastName, batch);
        students.add(student);
        return student;
    }

    // Read - Get all students
    public List<Student> getAllStudents() {
        return new ArrayList<>(students); // Return copy for encapsulation
    }

    // Read - Get student by ID
    public Student getStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null; // Student not found
    }

    // Read - Get active students only
    public List<Student> getActiveStudents() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    // Update - Update student information
    public boolean updateStudent(int id, String firstName, String lastName, String email, String batch) {
        Student student = getStudentById(id);
        if (student != null) {
            student.setFirstName(firstName);
            student.setLastName(lastName);
            student.setEmail(email);
            student.setBatch(batch);
            return true;
        }
        return false; // Student not found
    }

    // Delete (soft delete) - Deactivate student
    public boolean deactivateStudent(int id) {
        Student student = getStudentById(id);
        if (student != null) {
            student.setActive(false);
            return true;
        }
        return false; // Student not found
    }

    // Search students by name (partial match)
    public List<Student> searchStudentsByName(String name) {
        List<Student> result = new ArrayList<>();
        String searchTerm = name.toLowerCase();

        for (Student student : students) {
            if (student.getFirstName().toLowerCase().contains(searchTerm) ||
                    student.getLastName().toLowerCase().contains(searchTerm)) {
                result.add(student);
            }
        }
        return result;
    }

    // Get student count
    public int getStudentCount() {
        return students.size();
    }

    // Optional: Initialize with sample data
    private void initializeSampleData() {
        students.add(new Student(IDGenerator.getNextStudentId(), "Rohit", "Sharma", "rohit@example.com", "BatchA"));
        students.add(new Student(IDGenerator.getNextStudentId(), "Virat", "Kohli", "virat@example.com", "BatchB"));
        students.add(new Student(IDGenerator.getNextStudentId(), "MS", "Dhoni", "dhoni@example.com", "BatchC"));
    }
}