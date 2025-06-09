package com.smartstudent.ui;

import com.smartstudent.dao.StudentDAO;
import com.smartstudent.model.Student;

import java.util.List;
import java.util.Scanner;

public class ConsoleUI {
    // Constants for admin credentials
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "admin123";

    // DAO for student operations
    private final StudentDAO dao = new StudentDAO();
    // Scanner for user input
    private final Scanner scanner = new Scanner(System.in);

    // Start the console application
    public void start() {
        // Attempt to log in; if successful, display the menu
        if (login()) {
            menu();
        } else {
            System.out.println("Too many failed attempts. Exiting...");
        }
    }

    // Handle user login
    private boolean login() {
        System.out.println("=== SmartStudent Login ===");
        // Allow up to 3 login attempts
        for (int i = 0; i < 3; i++) {
            System.out.print("Username: ");
            String u = scanner.nextLine();
            System.out.print("Password: ");
            String p = scanner.nextLine();
            // Check if credentials are correct
            if (ADMIN_USERNAME.equals(u) && ADMIN_PASSWORD.equals(p)) {
                return true; // Successful login
            } else {
                System.out.println("Invalid credentials."); // Invalid attempt
            }
        }
        return false; // Login failed after 3 attempts
    }

    // Display the main menu and handle user choices
    private void menu() {
        while (true) {
            System.out.println("\n--- Menu ---");
            System.out.println("1. Add Student");
            System.out.println("2. View All Students");
            System.out.println("3. Update Student");
            System.out.println("4. Delete Student");
            System.out.println("5. Search Student");
            System.out.println("6. Exit");
            System.out.print("Choose: ");
            // Process user choice
            switch (scanner.nextLine().trim()) {
                case "1": add(); break;
                case "2": viewAll(); break;
                case "3": update(); break;
                case "4": delete(); break;
                case "5": search(); break;
                case "6": return; // Exit the menu
                default: System.out.println("Invalid option."); // Handle invalid input
            }
        }
    }

    // Add a new student
    private void add() {
        System.out.println("--- Add Student ---");
        Student s = new Student(); // Create a new Student object
        // Collect student details from user input
        System.out.print("Name: "); s.setName(scanner.nextLine());
        System.out.print("Roll No: "); s.setRollNo(scanner.nextLine());
        System.out.print("Department: "); s.setDepartment(scanner.nextLine());
        System.out.print("Email: "); s.setEmail(scanner.nextLine());
        System.out.print("Phone: "); s.setPhone(scanner.nextLine());
        System.out.print("Marks: "); s.setMarks(Float.parseFloat(scanner.nextLine()));

        // Attempt to add student via DAO
        if (dao.addStudent(s)) System.out.println("Student added!");
        else System.out.println("Failed to add."); // Handle failure
    }

    // View all students
    private void viewAll() {
        List<Student> list = dao.getAllStudents(); // Retrieve list of students
        // Print table header
        System.out.printf("%-5s %-20s %-10s %-15s %-25s %-15s %-7s%n", "ID", "Name", "Roll No", "Department", "Email", "Phone", "Marks");
        System.out.println("-----------------------------------------------------------------------------------------------");
        // Print each student
        for (Student s : list) System.out.println(s);
    }

    // Update an existing student
    private void update() {
        System.out.print("Enter Roll No or Name to search: ");
        Student s = dao.searchByRollOrName(scanner.nextLine()); // Search for student
        if (s == null) {
            System.out.println("Student not found."); // Handle case where student is not found
            return;
        }
        System.out.println("Leave blank to keep existing.");
        // Collect updated details, allowing blank entries to retain existing values
        System.out.print("Name (" + s.getName() + "): ");
        String name = scanner.nextLine();
        if (!name.isBlank()) s.setName(name);
        System.out.print("Roll No (" + s.getRollNo() + "): ");
        String roll = scanner.nextLine();
        if (!roll.isBlank()) s.setRollNo(roll);
        System.out.print("Dept (" + s.getDepartment() + "): ");
        String dept = scanner.nextLine();
        if (!dept.isBlank()) s.setDepartment(dept);
        System.out.print("Email (" + s.getEmail() + "): ");
        String email = scanner.nextLine();
        if (!email.isBlank()) s.setEmail(email);
        System.out.print("Phone (" + s.getPhone() + "): ");
        String phone = scanner.nextLine();
        if (!phone.isBlank()) s.setPhone(phone);
        System.out.print("Marks (" + s.getMarks() + "): ");
        String marks = scanner.nextLine();
        if (!marks.isBlank()) s.setMarks(Float.parseFloat(marks));

        // Attempt to update student via DAO
        if (dao.updateStudent(s)) System.out.println("Updated successfully.");
        else System.out.println("Update failed."); // Handle failure
    }

    // Search for students by various criteria
    private void search() {
        System.out.println("\n=== Search Student ===");
        System.out.print("Enter Roll No, Name, or Email to search: ");
        String keyword = scanner.nextLine().trim(); // Get search keyword

        List<Student> results = dao.searchByRollNameOrEmail(keyword); // Perform search

        // Check if results are found
        if (results.isEmpty()) {
            System.out.println("No students found with given keyword.");
        } else {
            // Print table header for results
            System.out.printf("%-5s %-20s %-10s %-15s %-25s %-15s %-7s%n",
                    "ID", "Name", "Roll No", "Department", "Email", "Phone", "Marks");
            System.out.println("-----------------------------------------------------------------------------------------------");
            // Print each found student
            for (Student s : results) {
                System.out.println(s);
            }
        }
    }

    // Delete a student
    private void delete() {
        System.out.print("Enter Roll No or Name to delete: ");
        Student s = dao.searchByRollOrName(scanner.nextLine()); // Search for student
        if (s == null) {
            System.out.println("Student not found."); // Handle case where student is not found
            return;
        }
        System.out.print("Are you sure to delete " + s.getName() + "? (yes/no): ");
        // Confirm deletion
        if (scanner.nextLine().equalsIgnoreCase("yes")) {
            // Attempt to delete student via DAO
            if (dao.deleteStudent(s.getId())) System.out.println("Deleted.");
            else System.out.println("Delete failed."); // Handle failure
        } else {
            System.out.println("Cancelled."); // Handle cancellation
        }
    }
}
