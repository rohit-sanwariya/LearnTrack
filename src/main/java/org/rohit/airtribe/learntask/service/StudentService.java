package org.rohit.airtribe.learntask.service;

import org.rohit.airtribe.learntask.entity.actors.student.Student;
import org.rohit.airtribe.learntask.exception.EntityNotFoundException;
import org.rohit.airtribe.learntask.exception.InvalidInputException;
import org.rohit.airtribe.learntask.util.IDGenerator;
import org.rohit.airtribe.learntask.util.InputValidator;
import java.util.ArrayList;
import java.util.List;

public class StudentService {
    private List<Student> students;

    public StudentService() {
        this.students = new ArrayList<>();
        initializeSampleData();
    }

    // Updated methods with exception handling

    public Student addStudent(String firstName, String lastName, String email, String batch)
            throws InvalidInputException {
        // Validate inputs
        String validatedFirstName = InputValidator.validateNonEmpty(firstName, "First Name");
        String validatedLastName = InputValidator.validateNonEmpty(lastName, "Last Name");
        String validatedEmail = InputValidator.validateEmail(email);
        String validatedBatch = InputValidator.validateNonEmpty(batch, "Batch");

        int newId = IDGenerator.getNextStudentId();
        Student student = new Student(newId, validatedFirstName, validatedLastName, validatedEmail, validatedBatch);
        students.add(student);
        return student;
    }

    // Method that throws EntityNotFoundException
    public Student getStudentById(int id) throws EntityNotFoundException {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        throw new EntityNotFoundException("Student", id);
    }

    // Update with exception
    public boolean updateStudent(int id, String firstName, String lastName, String email, String batch)
            throws EntityNotFoundException, InvalidInputException {

        Student student = getStudentById(id); // This throws EntityNotFoundException if not found

        // Validate inputs
        String validatedFirstName = InputValidator.validateNonEmpty(firstName, "First Name");
        String validatedLastName = InputValidator.validateNonEmpty(lastName, "Last Name");
        String validatedEmail = InputValidator.validateEmail(email);
        String validatedBatch = InputValidator.validateNonEmpty(batch, "Batch");

        student.setFirstName(validatedFirstName);
        student.setLastName(validatedLastName);
        student.setEmail(validatedEmail);
        student.setBatch(validatedBatch);
        return true;
    }

    public boolean deactivateStudent(int id) throws EntityNotFoundException {
        Student student = getStudentById(id); // Throws exception if not found
        student.setActive(false);
        return true;
    }

    // Keep old methods without exceptions for backward compatibility
    public List<Student> getAllStudents() {
        return new ArrayList<>(students);
    }

    public List<Student> getActiveStudents() {
        List<Student> activeStudents = new ArrayList<>();
        for (Student student : students) {
            if (student.isActive()) {
                activeStudents.add(student);
            }
        }
        return activeStudents;
    }

    public List<Student> searchStudentsByName(String name) {
        List<Student> result = new ArrayList<>();
        if (name == null || name.trim().isEmpty()) {
            return result;
        }

        String searchTerm = name.toLowerCase();
        for (Student student : students) {
            if (student.getFirstName().toLowerCase().contains(searchTerm) ||
                    student.getLastName().toLowerCase().contains(searchTerm)) {
                result.add(student);
            }
        }
        return result;
    }

    public int getStudentCount() {
        return students.size();
    }

    private void initializeSampleData() {
        try {
            students.add(new Student(IDGenerator.getNextStudentId(), "Rohit", "Sharma", "rohit@example.com", "BatchA"));
            students.add(new Student(IDGenerator.getNextStudentId(), "Virat", "Kohli", "virat@example.com", "BatchB"));
            students.add(new Student(IDGenerator.getNextStudentId(), "MS", "Dhoni", "dhoni@example.com", "BatchC"));
        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }
    }
}