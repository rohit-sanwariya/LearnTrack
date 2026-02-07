package org.rohit.airtribe.learntask.ui;

import org.rohit.airtribe.learntask.entity.enrollment.EntrollmentStatus;
import org.rohit.airtribe.learntask.service.StudentService;
import org.rohit.airtribe.learntask.service.CourseService;
import org.rohit.airtribe.learntask.service.EnrollmentService;
import org.rohit.airtribe.learntask.entity.actors.student.Student;
import org.rohit.airtribe.learntask.entity.course.Course;
import org.rohit.airtribe.learntask.entity.enrollment.Enrollment;
import org.rohit.airtribe.learntask.exception.EntityNotFoundException;
import org.rohit.airtribe.learntask.exception.InvalidInputException;
import org.rohit.airtribe.learntask.util.InputValidator;
import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    private Scanner scanner;
    private StudentService studentService;
    private CourseService courseService;
    private EnrollmentService enrollmentService;
    private boolean isRunning;

    public ConsoleUI() {
        this.scanner = new Scanner(System.in);
        this.studentService = new StudentService();
        this.courseService = new CourseService();
        this.enrollmentService = new EnrollmentService();
        this.isRunning = true;
    }

    public void start() {
        displayWelcomeMessage();

        while (isRunning) {
            displayMainMenu();
            int choice = getMenuChoice(1, 5);
            processMainMenuChoice(choice);
        }

        displayExitMessage();
        scanner.close();
    }

    // ==================== MAIN MENU ====================

    private void displayMainMenu() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("         LEARNTRACK - MAIN MENU");
        System.out.println("=".repeat(50));
        System.out.println("1. Student Management");
        System.out.println("2. Course Management");
        System.out.println("3. Enrollment Management");
        System.out.println("4. View System Statistics");
        System.out.println("5. Exit");
        System.out.println("=".repeat(50));
        System.out.print("Enter your choice (1-5): ");
    }

    private void processMainMenuChoice(int choice) {
        switch (choice) {
            case 1 -> studentManagementMenu();
            case 2 -> courseManagementMenu();
            case 3 -> enrollmentManagementMenu();
            case 4 -> displaySystemStatistics();
            case 5 -> isRunning = false;
            default -> System.out.println("Invalid choice. Please try again.");
        }
    }

    // ==================== STUDENT MANAGEMENT ====================

    private void studentManagementMenu() {
        boolean inStudentMenu = true;

        while (inStudentMenu) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("       STUDENT MANAGEMENT");
            System.out.println("-".repeat(40));
            System.out.println("1. Add New Student");
            System.out.println("2. View All Students");
            System.out.println("3. Search Student by ID");
            System.out.println("4. Search Student by Name");
            System.out.println("5. Update Student");
            System.out.println("6. Deactivate Student");
            System.out.println("7. View Active Students");
            System.out.println("8. Back to Main Menu");
            System.out.println("-".repeat(40));
            System.out.print("Enter your choice (1-8): ");

            int choice = getMenuChoice(1, 8);

            switch (choice) {
                case 1 -> addNewStudent();
                case 2 -> viewAllStudents();
                case 3 -> searchStudentById();
                case 4 -> searchStudentByName();
                case 5 -> updateStudent();
                case 6 -> deactivateStudent();
                case 7 -> viewActiveStudents();
                case 8 -> inStudentMenu = false;
            }
        }
    }

    private void addNewStudent() {
        System.out.println("\n--- Add New Student ---");

        try {
            System.out.print("Enter First Name: ");
            String firstName = scanner.nextLine();

            System.out.print("Enter Last Name: ");
            String lastName = scanner.nextLine();

            System.out.print("Enter Email: ");
            String email = scanner.nextLine();

            System.out.print("Enter Batch: ");
            String batch = scanner.nextLine();

            Student student = studentService.addStudent(firstName, lastName, email, batch);
            System.out.println("✓ Student added successfully!");
            System.out.println("  Student ID: " + student.getId());
            System.out.println("  Name: " + student.getDisplayName());

        } catch (InvalidInputException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Unexpected error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void viewAllStudents() {
        System.out.println("\n--- All Students ---");
        List<Student> students = studentService.getAllStudents();

        if (students.isEmpty()) {
            System.out.println("No students found.");
        } else {
            System.out.println("Total Students: " + students.size());
            System.out.println("-".repeat(60));
            System.out.printf("%-8s %-20s %-25s %-10s %s%n",
                    "ID", "Name", "Email", "Batch", "Status");
            System.out.println("-".repeat(60));

            for (Student student : students) {
                String status = student.isActive() ? "Active" : "Inactive";
                System.out.printf("%-8d %-20s %-25s %-10s %s%n",
                        student.getId(),
                        student.getFirstName() + " " + student.getLastName(),
                        student.getEmail(),
                        student.getBatch(),
                        status);
            }
        }

        pressEnterToContinue();
    }

    private void searchStudentById() {
        System.out.println("\n--- Search Student by ID ---");

        try {
            System.out.print("Enter Student ID: ");
            String input = scanner.nextLine();
            int studentId = InputValidator.validatePositiveInteger(input, "Student ID");

            Student student = studentService.getStudentById(studentId);
            displayStudentDetails(student);

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void searchStudentByName() {
        System.out.println("\n--- Search Student by Name ---");
        System.out.print("Enter name (or part of name): ");
        String searchTerm = scanner.nextLine();

        List<Student> results = studentService.searchStudentsByName(searchTerm);

        if (results.isEmpty()) {
            System.out.println("No students found matching: '" + searchTerm + "'");
        } else {
            System.out.println("Found " + results.size() + " student(s):");
            for (Student student : results) {
                System.out.println("  ID: " + student.getId() +
                        ", Name: " + student.getDisplayName() +
                        ", Email: " + student.getEmail());
            }
        }

        pressEnterToContinue();
    }

    private void updateStudent() {
        System.out.println("\n--- Update Student ---");

        try {
            System.out.print("Enter Student ID to update: ");
            String idInput = scanner.nextLine();
            int studentId = InputValidator.validatePositiveInteger(idInput, "Student ID");

            // First, get the student to show current values
            Student student = studentService.getStudentById(studentId);
            System.out.println("Current details:");
            displayStudentDetails(student);

            System.out.println("\nEnter new details (press Enter to keep current value):");

            System.out.print("First Name [" + student.getFirstName() + "]: ");
            String firstName = scanner.nextLine();
            if (firstName.isEmpty()) firstName = student.getFirstName();

            System.out.print("Last Name [" + student.getLastName() + "]: ");
            String lastName = scanner.nextLine();
            if (lastName.isEmpty()) lastName = student.getLastName();

            System.out.print("Email [" + student.getEmail() + "]: ");
            String email = scanner.nextLine();
            if (email.isEmpty()) email = student.getEmail();

            System.out.print("Batch [" + student.getBatch() + "]: ");
            String batch = scanner.nextLine();
            if (batch.isEmpty()) batch = student.getBatch();

            boolean updated = studentService.updateStudent(studentId, firstName, lastName, email, batch);
            if (updated) {
                System.out.println("✓ Student updated successfully!");
            }

        } catch (InvalidInputException | EntityNotFoundException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void deactivateStudent() {
        System.out.println("\n--- Deactivate Student ---");

        try {
            System.out.print("Enter Student ID to deactivate: ");
            String input = scanner.nextLine();
            int studentId = InputValidator.validatePositiveInteger(input, "Student ID");

            boolean deactivated = studentService.deactivateStudent(studentId);
            if (deactivated) {
                System.out.println("✓ Student deactivated successfully!");
            }

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void viewActiveStudents() {
        System.out.println("\n--- Active Students ---");
        List<Student> activeStudents = studentService.getActiveStudents();

        if (activeStudents.isEmpty()) {
            System.out.println("No active students found.");
        } else {
            System.out.println("Active Students: " + activeStudents.size());
            for (Student student : activeStudents) {
                System.out.println("  ID: " + student.getId() +
                        ", Name: " + student.getDisplayName() +
                        ", Batch: " + student.getBatch());
            }
        }

        pressEnterToContinue();
    }

    private void displayStudentDetails(Student student) {
        System.out.println("\nStudent Details:");
        System.out.println("-".repeat(40));
        System.out.println("ID: " + student.getId());
        System.out.println("Name: " + student.getDisplayName());
        System.out.println("Email: " + student.getEmail());
        System.out.println("Batch: " + student.getBatch());
        System.out.println("Status: " + (student.isActive() ? "Active" : "Inactive"));
        System.out.println("-".repeat(40));
    }

    // ==================== COURSE MANAGEMENT ====================

    private void courseManagementMenu() {
        boolean inCourseMenu = true;

        while (inCourseMenu) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("       COURSE MANAGEMENT");
            System.out.println("-".repeat(40));
            System.out.println("1. Add New Course");
            System.out.println("2. View All Courses");
            System.out.println("3. Search Course by ID");
            System.out.println("4. Update Course");
            System.out.println("5. Activate/Deactivate Course");
            System.out.println("6. View Active Courses");
            System.out.println("7. Back to Main Menu");
            System.out.println("-".repeat(40));
            System.out.print("Enter your choice (1-7): ");

            int choice = getMenuChoice(1, 7);

            switch (choice) {
                case 1 -> addNewCourse();
                case 2 -> viewAllCourses();
                case 3 -> searchCourseById();
                case 4 -> updateCourse();
                case 5 -> toggleCourseStatus();
                case 6 -> viewActiveCourses();
                case 7 -> inCourseMenu = false;
            }
        }
    }

    private void addNewCourse() {
        System.out.println("\n--- Add New Course ---");

        try {
            System.out.print("Enter Course Name: ");
            String courseName = InputValidator.validateNonEmpty(scanner.nextLine(), "Course Name");

            System.out.print("Enter Description: ");
            String description = scanner.nextLine();
            if (description.isEmpty()) description = "No description";

            System.out.print("Enter Duration (in weeks): ");
            String durationInput = scanner.nextLine();
            int duration = InputValidator.validatePositiveInteger(durationInput, "Duration");

            Course course = courseService.addCourse(courseName, description, duration);
            System.out.println("✓ Course added successfully!");
            System.out.println("  Course ID: " + course.getId());
            System.out.println("  Course Name: " + course.getCourseName());

        } catch (InvalidInputException e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void viewAllCourses() {
        System.out.println("\n--- All Courses ---");
        List<Course> courses = courseService.getAllCourses();

        if (courses.isEmpty()) {
            System.out.println("No courses found.");
        } else {
            System.out.println("Total Courses: " + courses.size());
            System.out.println("-".repeat(70));
            System.out.printf("%-8s %-25s %-20s %-10s %s%n",
                    "ID", "Course Name", "Description", "Duration", "Status");
            System.out.println("-".repeat(70));

            for (Course course : courses) {
                String status = course.isActive() ? "Active" : "Inactive";
                // Truncate description if too long
                String shortDesc = course.getDescription().length() > 18 ?
                        course.getDescription().substring(0, 15) + "..." : course.getDescription();

                System.out.printf("%-8d %-25s %-20s %-10d %s%n",
                        course.getId(),
                        course.getCourseName(),
                        shortDesc,
                        course.getDurationInWeeks(),
                        status);
            }
        }

        pressEnterToContinue();
    }

    private void searchCourseById() {
        System.out.println("\n--- Search Course by ID ---");

        try {
            System.out.print("Enter Course ID: ");
            String input = scanner.nextLine();
            int courseId = InputValidator.validatePositiveInteger(input, "Course ID");

            Course course = courseService.getCourseById(courseId);
            displayCourseDetails(course);

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Course not found or error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void updateCourse() {
        System.out.println("\n--- Update Course ---");

        try {
            System.out.print("Enter Course ID to update: ");
            String idInput = scanner.nextLine();
            int courseId = InputValidator.validatePositiveInteger(idInput, "Course ID");

            Course course = courseService.getCourseById(courseId);
            System.out.println("Current details:");
            displayCourseDetails(course);

            System.out.println("\nEnter new details (press Enter to keep current value):");

            System.out.print("Course Name [" + course.getCourseName() + "]: ");
            String courseName = scanner.nextLine();
            if (courseName.isEmpty()) courseName = course.getCourseName();

            System.out.print("Description [" + course.getDescription() + "]: ");
            String description = scanner.nextLine();
            if (description.isEmpty()) description = course.getDescription();

            System.out.print("Duration [" + course.getDurationInWeeks() + "]: ");
            String durationInput = scanner.nextLine();
            int duration;
            if (durationInput.isEmpty()) {
                duration = course.getDurationInWeeks();
            } else {
                duration = InputValidator.validatePositiveInteger(durationInput, "Duration");
            }

            boolean updated = courseService.updateCourse(courseId, courseName, description, duration);
            if (updated) {
                System.out.println("✓ Course updated successfully!");
            }

        } catch (InvalidInputException e) {
            System.out.println("✗ Error: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Course not found or error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void toggleCourseStatus() {
        System.out.println("\n--- Toggle Course Status ---");

        try {
            System.out.print("Enter Course ID: ");
            String input = scanner.nextLine();
            int courseId = InputValidator.validatePositiveInteger(input, "Course ID");

            Course course = courseService.getCourseById(courseId);
            System.out.println("Current status: " + (course.isActive() ? "Active" : "Inactive"));

            System.out.print("Do you want to " + (course.isActive() ? "DEACTIVATE" : "ACTIVATE") +
                    " this course? (yes/no): ");
            String confirmation = scanner.nextLine().toLowerCase();

            if (confirmation.equals("yes") || confirmation.equals("y")) {
                boolean success;
                if (course.isActive()) {
                    success = courseService.deactivateCourse(courseId);
                    System.out.println(success ? "✓ Course deactivated!" : "Failed to deactivate course.");
                } else {
                    success = courseService.activateCourse(courseId);
                    System.out.println(success ? "✓ Course activated!" : "Failed to activate course.");
                }
            } else {
                System.out.println("Operation cancelled.");
            }

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Course not found or error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void viewActiveCourses() {
        System.out.println("\n--- Active Courses ---");
        List<Course> activeCourses = courseService.getActiveCourses();

        if (activeCourses.isEmpty()) {
            System.out.println("No active courses found.");
        } else {
            System.out.println("Active Courses: " + activeCourses.size());
            for (Course course : activeCourses) {
                System.out.println("  ID: " + course.getId() +
                        ", Name: " + course.getCourseName() +
                        ", Duration: " + course.getDurationInWeeks() + " weeks");
            }
        }

        pressEnterToContinue();
    }

    private void displayCourseDetails(Course course) {
        System.out.println("\nCourse Details:");
        System.out.println("-".repeat(40));
        System.out.println("ID: " + course.getId());
        System.out.println("Name: " + course.getCourseName());
        System.out.println("Description: " + course.getDescription());
        System.out.println("Duration: " + course.getDurationInWeeks() + " weeks");
        System.out.println("Status: " + (course.isActive() ? "Active" : "Inactive"));
        System.out.println("-".repeat(40));
    }

    // ==================== ENROLLMENT MANAGEMENT ====================

    private void enrollmentManagementMenu() {
        boolean inEnrollmentMenu = true;

        while (inEnrollmentMenu) {
            System.out.println("\n" + "-".repeat(40));
            System.out.println("     ENROLLMENT MANAGEMENT");
            System.out.println("-".repeat(40));
            System.out.println("1. Enroll Student in Course");
            System.out.println("2. View All Enrollments");
            System.out.println("3. View Enrollments by Student");
            System.out.println("4. View Enrollments by Course");
            System.out.println("5. Update Enrollment Status");
            System.out.println("6. Back to Main Menu");
            System.out.println("-".repeat(40));
            System.out.print("Enter your choice (1-6): ");

            int choice = getMenuChoice(1, 6);

            switch (choice) {
                case 1 -> enrollStudentInCourse();
                case 2 -> viewAllEnrollments();
                case 3 -> viewEnrollmentsByStudent();
                case 4 -> viewEnrollmentsByCourse();
                case 5 -> updateEnrollmentStatus();
                case 6 -> inEnrollmentMenu = false;
            }
        }
    }

    private void enrollStudentInCourse() {
        System.out.println("\n--- Enroll Student in Course ---");

        try {
            // Get student ID
            System.out.print("Enter Student ID: ");
            String studentIdInput = scanner.nextLine();
            int studentId = InputValidator.validatePositiveInteger(studentIdInput, "Student ID");

            // Verify student exists
            Student student = studentService.getStudentById(studentId);
            if (!student.isActive()) {
                System.out.println("✗ Cannot enroll inactive student.");
                pressEnterToContinue();
                return;
            }

            // Get course ID
            System.out.print("Enter Course ID: ");
            String courseIdInput = scanner.nextLine();
            int courseId = InputValidator.validatePositiveInteger(courseIdInput, "Course ID");

            // Verify course exists
            Course course = courseService.getCourseById(courseId);
            if (!course.isActive()) {
                System.out.println("✗ Cannot enroll in inactive course.");
                pressEnterToContinue();
                return;
            }

            // Get enrollment date
            System.out.print("Enter Enrollment Date (YYYY-MM-DD): ");
            String enrollmentDate = InputValidator.validateDate(scanner.nextLine());

            // Create enrollment
            Enrollment enrollment = enrollmentService.enrollStudent(studentId, courseId, enrollmentDate);

            System.out.println("✓ Enrollment successful!");
            System.out.println("  Enrollment ID: " + enrollment.getId());
            System.out.println("  Student: " + student.getDisplayName());
            System.out.println("  Course: " + course.getCourseName());
            System.out.println("  Status: " + enrollment.getStatus());

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void viewAllEnrollments() {
        System.out.println("\n--- All Enrollments ---");
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();

        if (enrollments.isEmpty()) {
            System.out.println("No enrollments found.");
        } else {
            System.out.println("Total Enrollments: " + enrollments.size());
            System.out.println("-".repeat(80));
            System.out.printf("%-12s %-12s %-12s %-15s %-15s%n",
                    "Enrollment ID", "Student ID", "Course ID", "Date", "Status");
            System.out.println("-".repeat(80));

            for (Enrollment enrollment : enrollments) {
                System.out.printf("%-12d %-12d %-12d %-15s %-15s%n",
                        enrollment.getId(),
                        enrollment.getStudentId(),
                        enrollment.getCourseId(),
                        enrollment.getEnrollmentDate(),
                        enrollment.getStatus());
            }
        }

        pressEnterToContinue();
    }

    private void viewEnrollmentsByStudent() {
        System.out.println("\n--- Enrollments by Student ---");

        try {
            System.out.print("Enter Student ID: ");
            String studentIdInput = scanner.nextLine();
            int studentId = InputValidator.validatePositiveInteger(studentIdInput, "Student ID");

            // Verify student exists
            Student student = studentService.getStudentById(studentId);
            System.out.println("Student: " + student.getDisplayName());

            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByStudentId(studentId);

            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this student.");
            } else {
                System.out.println("Enrollments: " + enrollments.size());
                System.out.println("-".repeat(70));
                for (Enrollment enrollment : enrollments) {
                    try {
                        Course course = courseService.getCourseById(enrollment.getCourseId());
                        System.out.println("  Course: " + course.getCourseName() +
                                " (ID: " + enrollment.getCourseId() + ")" +
                                ", Date: " + enrollment.getEnrollmentDate() +
                                ", Status: " + enrollment.getStatus());
                    } catch (Exception e) {
                        System.out.println("  Course ID: " + enrollment.getCourseId() +
                                " (Course not found)" +
                                ", Date: " + enrollment.getEnrollmentDate() +
                                ", Status: " + enrollment.getStatus());
                    }
                }
            }

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (EntityNotFoundException e) {
            System.out.println("✗ " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void viewEnrollmentsByCourse() {
        System.out.println("\n--- Enrollments by Course ---");

        try {
            System.out.print("Enter Course ID: ");
            String courseIdInput = scanner.nextLine();
            int courseId = InputValidator.validatePositiveInteger(courseIdInput, "Course ID");

            // Verify course exists
            Course course = courseService.getCourseById(courseId);
            System.out.println("Course: " + course.getCourseName());

            List<Enrollment> enrollments = enrollmentService.getEnrollmentsByCourseId(courseId);

            if (enrollments.isEmpty()) {
                System.out.println("No enrollments found for this course.");
            } else {
                System.out.println("Enrollments: " + enrollments.size());
                System.out.println("-".repeat(70));
                for (Enrollment enrollment : enrollments) {
                    try {
                        Student student = studentService.getStudentById(enrollment.getStudentId());
                        System.out.println("  Student: " + student.getDisplayName() +
                                " (ID: " + enrollment.getStudentId() + ")" +
                                ", Date: " + enrollment.getEnrollmentDate() +
                                ", Status: " + enrollment.getStatus());
                    } catch (Exception e) {
                        System.out.println("  Student ID: " + enrollment.getStudentId() +
                                " (Student not found)" +
                                ", Date: " + enrollment.getEnrollmentDate() +
                                ", Status: " + enrollment.getStatus());
                    }
                }
            }

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Course not found or error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    private void updateEnrollmentStatus() {
        System.out.println("\n--- Update Enrollment Status ---");

        try {
            System.out.print("Enter Enrollment ID: ");
            String enrollmentIdInput = scanner.nextLine();
            int enrollmentId = InputValidator.validatePositiveInteger(enrollmentIdInput, "Enrollment ID");

            Enrollment enrollment = enrollmentService.getEnrollmentById(enrollmentId);
            System.out.println("Current status: " + enrollment.getStatus());

            System.out.println("\nAvailable statuses:");
            System.out.println("1. ACTIVE");
            System.out.println("2. COMPLETED");
            System.out.println("3. CANCELLED");
            System.out.print("Select new status (1-3): ");

            int statusChoice = getMenuChoice(1, 3);
            EntrollmentStatus newStatus = switch (statusChoice) {
                case 1 ->  EntrollmentStatus.ACTIVE;
                case 2 -> EntrollmentStatus.COMPLETED;
                case 3 -> EntrollmentStatus.CANCELLED;
                default -> throw new IllegalStateException("Unexpected value: " + statusChoice);
            };

            boolean updated = enrollmentService.updateEnrollmentStatus(enrollmentId, newStatus);
            if (updated) {
                System.out.println("✓ Enrollment status updated to: " + newStatus);
            } else {
                System.out.println("✗ Failed to update enrollment status.");
            }

        } catch (InvalidInputException e) {
            System.out.println("✗ Invalid input: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("✗ Enrollment not found or error: " + e.getMessage());
        }

        pressEnterToContinue();
    }

    // ==================== SYSTEM STATISTICS ====================

    private void displaySystemStatistics() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("       SYSTEM STATISTICS");
        System.out.println("=".repeat(50));

        int totalStudents = studentService.getStudentCount();
        int activeStudents = studentService.getActiveStudents().size();

        int totalCourses = courseService.getCourseCount();
        int activeCourses = courseService.getActiveCourses().size();

        int totalEnrollments = enrollmentService.getEnrollmentCount();
        int activeEnrollments = enrollmentService.getActiveEnrollmentCount();

        System.out.println("STUDENTS:");
        System.out.println("  Total Students: " + totalStudents);
        System.out.println("  Active Students: " + activeStudents);
        System.out.println("  Inactive Students: " + (totalStudents - activeStudents));

        System.out.println("\nCOURSES:");
        System.out.println("  Total Courses: " + totalCourses);
        System.out.println("  Active Courses: " + activeCourses);
        System.out.println("  Inactive Courses: " + (totalCourses - activeCourses));

        System.out.println("\nENROLLMENTS:");
        System.out.println("  Total Enrollments: " + totalEnrollments);
        System.out.println("  Active Enrollments: " + activeEnrollments);
        System.out.println("  Completed Enrollments: " +
                countEnrollmentsByStatus("COMPLETED"));
        System.out.println("  Cancelled Enrollments: " +
                countEnrollmentsByStatus("CANCELLED"));

        System.out.println("\n" + "=".repeat(50));
        pressEnterToContinue();
    }

    private int countEnrollmentsByStatus(String status) {
        int count = 0;
        List<Enrollment> enrollments = enrollmentService.getAllEnrollments();
        for (Enrollment enrollment : enrollments) {
            if (status.equals(enrollment.getStatus())) {
                count++;
            }
        }
        return count;
    }

    // ==================== HELPER METHODS ====================

    private int getMenuChoice(int min, int max) {
        while (true) {
            try {
                String input = scanner.nextLine();
                int choice = Integer.parseInt(input);

                if (choice >= min && choice <= max) {
                    return choice;
                } else {
                    System.out.print("Please enter a number between " + min + " and " + max + ": ");
                }
            } catch (NumberFormatException e) {
                System.out.print("Invalid input. Please enter a number: ");
            }
        }
    }

    private void pressEnterToContinue() {
        System.out.print("\nPress Enter to continue...");
        scanner.nextLine();
    }

    private void displayWelcomeMessage() {
        System.out.println("╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║         WELCOME TO LEARNTRACK                        ║");
        System.out.println("║      Student & Course Management System              ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
        System.out.println("\nDeveloped by: Rohit Airtribe");
        System.out.println("Version: 1.0.0");
        System.out.println("\nLoading system...");

        // Simulate loading
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }
    }

    private void displayExitMessage() {
        System.out.println("\n╔══════════════════════════════════════════════════════╗");
        System.out.println("║                                                      ║");
        System.out.println("║          Thank you for using LearnTrack!             ║");
        System.out.println("║                                                      ║");
        System.out.println("║          System shutting down...                     ║");
        System.out.println("║                                                      ║");
        System.out.println("╚══════════════════════════════════════════════════════╝");
    }
}