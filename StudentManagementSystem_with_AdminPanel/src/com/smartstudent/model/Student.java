package com.smartstudent.model;

public class Student {
    // Member variables representing student attributes
    private int id; // Unique identifier for the student
    private String name; // Student's name
    private String rollNo; // Student's roll number
    private String department; // Department in which the student is enrolled
    private String email; // Student's email address
    private String phone; // Student's phone number
    private float marks; // Student's marks

    // Default constructor
    public Student() {}

    // Constructor to initialize a student without an ID
    public Student(String name, String rollNo, String department, String email, String phone, float marks) {
        this.name = name;
        this.rollNo = rollNo;
        this.department = department;
        this.email = email;
        this.phone = phone;
        this.marks = marks;
    }

    // Constructor to initialize a student with all attributes including ID
    public Student(int id, String name, String rollNo, String department, String email, String phone, float marks) {
        this(name, rollNo, department, email, phone, marks); // Call the other constructor
        this.id = id; // Set the ID
    }

    // Getters and Setters for each member variable
    public int getId() { return id; } // Get the student's ID
    public String getName() { return name; } // Get the student's name
    public String getRollNo() { return rollNo; } // Get the student's roll number
    public String getDepartment() { return department; } // Get the student's department
    public String getEmail() { return email; } // Get the student's email
    public String getPhone() { return phone; } // Get the student's phone number
    public float getMarks() { return marks; } // Get the student's marks

    public void setId(int id) { this.id = id; } // Set the student's ID
    public void setName(String name) { this.name = name; } // Set the student's name
    public void setRollNo(String rollNo) { this.rollNo = rollNo; } // Set the student's roll number
    public void setDepartment(String department) { this.department = department; } // Set the student's department
    public void setEmail(String email) { this.email = email; } // Set the student's email
    public void setPhone(String phone) { this.phone = phone; } // Set the student's phone number
    public void setMarks(float marks) { this.marks = marks; } // Set the student's marks

    // Override toString method to provide a formatted string representation of the student
    @Override
    public String toString() {
        return String.format("%-5d %-20s %-10s %-15s %-25s %-15s %-7.2f", id, name, rollNo, department, email, phone, marks);
    }
}
