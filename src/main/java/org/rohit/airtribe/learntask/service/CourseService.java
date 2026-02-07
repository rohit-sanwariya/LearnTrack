package org.rohit.airtribe.learntask.service;

import org.rohit.airtribe.learntask.entity.course.Course;
import org.rohit.airtribe.learntask.util.IDGenerator;
import java.util.ArrayList;
import java.util.List;

public class CourseService {
    private List<Course> courses;

    public CourseService() {
        this.courses = new ArrayList<>();
        initializeSampleData();
    }

    // CRUD Operations

    public Course addCourse(String courseName, String description, int durationInWeeks) {
        int newId = IDGenerator.getNextCourseId();
        Course course = new Course(newId, courseName, description, durationInWeeks);
        courses.add(course);
        return course;
    }

    // Method overloading example
    public Course addCourse(String courseName) {
        int newId = IDGenerator.getNextCourseId();
        Course course = new Course(newId, courseName);
        courses.add(course);
        return course;
    }

    public List<Course> getAllCourses() {
        return new ArrayList<>(courses);
    }

    public Course getCourseById(int id) {
        for (Course course : courses) {
            if (course.getId() == id) {
                return course;
            }
        }
        return null;
    }

    public List<Course> getActiveCourses() {
        List<Course> activeCourses = new ArrayList<>();
        for (Course course : courses) {
            if (course.isActive()) {
                activeCourses.add(course);
            }
        }
        return activeCourses;
    }

    public boolean updateCourse(int id, String courseName, String description, int durationInWeeks) {
        Course course = getCourseById(id);
        if (course != null) {
            course.setCourseName(courseName);
            course.setDescription(description);
            course.setDurationInWeeks(durationInWeeks);
            return true;
        }
        return false;
    }

    public boolean deactivateCourse(int id) {
        Course course = getCourseById(id);
        if (course != null) {
            course.setActive(false);
            return true;
        }
        return false;
    }

    public boolean activateCourse(int id) {
        Course course = getCourseById(id);
        if (course != null) {
            course.setActive(true);
            return true;
        }
        return false;
    }

    public int getCourseCount() {
        return courses.size();
    }

    private void initializeSampleData() {
        courses.add(new Course(IDGenerator.getNextCourseId(), "Java Programming", "Learn Java from scratch", 12));
        courses.add(new Course(IDGenerator.getNextCourseId(), "Data Structures", "Algorithms and data structures", 10));
        courses.add(new Course(IDGenerator.getNextCourseId(), "Web Development", "HTML, CSS, JavaScript", 8));
    }
}