package org.rohit.airtribe.learntask.entity.actors.student;

import org.rohit.airtribe.learntask.entity.actors.person.Person;

public class Student extends Person {
    private String batch;
    private boolean active;

    public Student(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }



    public Student(int id, String firstName, String lastName, String batch) {
        super(id, firstName, lastName); // Calls parent constructor
        this.batch = batch;
        this.active = true;
    }

    public Student(int id, String firstName, String lastName, String email, String batch) {
        super(id, firstName, lastName, email); // Calls parent constructor with email
        this.batch = batch;
        this.active = true;
    }

    public String getBatch() {
        return batch;
    }

    public void setBatch(String batch) {
        this.batch = batch;
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    @Override
    public String toString() {
        return "Student{" +
                "batch='" + batch + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public String getDisplayName() {
        return super.getDisplayName() + " (Batch: " + batch + ")";
    }
}
