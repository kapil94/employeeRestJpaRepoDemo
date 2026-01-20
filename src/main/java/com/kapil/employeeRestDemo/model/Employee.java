package com.kapil.employeeRestDemo.model;

import jakarta.persistence.*;

@Entity
public class Employee {

    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    private int id;


    private String firstName;

    private String lastName;

    private String email;

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmail() {
        return email;
    }
}
