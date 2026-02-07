package org.rohit.airtribe.learntask.entity.actors.person;

public class Person {
    private int id;
    private String firstName;
    private String lastName;
    private String email;

    public int getId() {
        return id;
    }

    public Person(int id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Person(int id, String firstName, String lastName, String email) {
        this(id, firstName, lastName); // Constructor chaining
        this.email = email;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }
    public String getDisplayName() {
        return firstName + " " + lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
