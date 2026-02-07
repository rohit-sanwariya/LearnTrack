package org.rohit.airtribe.learntask.util;

public class IDGenerator {
    // Static variables for ID counters
    private static int studentIdCounter = 1000;
    private static int courseIdCounter = 2000;
    private static int enrollmentIdCounter = 3000;

    // Private constructor to prevent instantiation (utility class pattern)
    private IDGenerator() {
        // Utility class - no instantiation needed
    }

    // Static methods to generate IDs
    public static int getNextStudentId() {
        return studentIdCounter++;
    }

    public static int getNextCourseId() {
        return courseIdCounter++;
    }

    public static int getNextEnrollmentId() {
        return enrollmentIdCounter++;
    }

    // Reset methods (optional, for testing)
    public static void resetStudentIdCounter() {
        studentIdCounter = 1000;
    }

    public static void resetCourseIdCounter() {
        courseIdCounter = 2000;
    }

    public static void resetEnrollmentIdCounter() {
        enrollmentIdCounter = 3000;
    }
}