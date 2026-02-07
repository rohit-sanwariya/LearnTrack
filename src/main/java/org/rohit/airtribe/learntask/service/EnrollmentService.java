package org.rohit.airtribe.learntask.service;

import org.rohit.airtribe.learntask.entity.enrollment.Enrollment;
import org.rohit.airtribe.learntask.entity.enrollment.EntrollmentStatus;
import org.rohit.airtribe.learntask.util.IDGenerator;
import java.util.ArrayList;
import java.util.List;

public class EnrollmentService {
    private List<Enrollment> enrollments;

    public EnrollmentService() {
        this.enrollments = new ArrayList<>();
        initializeSampleData();
    }

    // CRUD Operations

    public Enrollment enrollStudent(int studentId, int courseId, String enrollmentDate) {
        int newId = IDGenerator.getNextEnrollmentId();
        Enrollment enrollment = new Enrollment(newId, studentId, courseId, enrollmentDate);
        enrollments.add(enrollment);
        return enrollment;
    }

    public List<Enrollment> getAllEnrollments() {
        return new ArrayList<>(enrollments);
    }

    public Enrollment getEnrollmentById(int id) {
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getId() == id) {
                return enrollment;
            }
        }
        return null;
    }

    public List<Enrollment> getEnrollmentsByStudentId(int studentId) {
        List<Enrollment> studentEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getStudentId() == studentId) {
                studentEnrollments.add(enrollment);
            }
        }
        return studentEnrollments;
    }

    public List<Enrollment> getEnrollmentsByCourseId(int courseId) {
        List<Enrollment> courseEnrollments = new ArrayList<>();
        for (Enrollment enrollment : enrollments) {
            if (enrollment.getCourseId() == courseId) {
                courseEnrollments.add(enrollment);
            }
        }
        return courseEnrollments;
    }

    public boolean updateEnrollmentStatus(int enrollmentId, EntrollmentStatus newStatus) {
        Enrollment enrollment = getEnrollmentById(enrollmentId);
        if (enrollment != null) {
            enrollment.setStatus(newStatus);
            return true;
        }
        return false;
    }

    public boolean cancelEnrollment(int enrollmentId) {
        return updateEnrollmentStatus(enrollmentId, EntrollmentStatus.CANCELLED);
    }

    public boolean completeEnrollment(int enrollmentId) {
        return updateEnrollmentStatus(enrollmentId, EntrollmentStatus.COMPLETED);
    }

    public int getEnrollmentCount() {
        return enrollments.size();
    }

    public int getActiveEnrollmentCount() {
        int count = 0;
        for (Enrollment enrollment : enrollments) {
            if (EntrollmentStatus.ACTIVE.equals(enrollment.getStatus())) {
                count++;
            }
        }
        return count;
    }

    private void initializeSampleData() {
        // Sample enrollments linking student 1000+ with course 2000+
        enrollments.add(new Enrollment(IDGenerator.getNextEnrollmentId(), 1000, 2000, "2024-01-10", EntrollmentStatus.ACTIVE));
        enrollments.add(new Enrollment(IDGenerator.getNextEnrollmentId(), 1001, 2001, "2024-01-12", EntrollmentStatus.COMPLETED));
        enrollments.add(new Enrollment(IDGenerator.getNextEnrollmentId(), 1002, 2002, "2024-01-15", EntrollmentStatus.ACTIVE));
    }
}