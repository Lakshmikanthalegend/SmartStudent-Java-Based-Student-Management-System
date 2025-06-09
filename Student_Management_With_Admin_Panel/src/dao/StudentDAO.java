package dao;

import db.DatabaseUtil;
import model.Student;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class StudentDAO {

    // Method to add a new student to the database
    public boolean addStudent(Student student) {
        String sql = "INSERT INTO students (name, roll_no, department, email, phone, marks) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection conn = DatabaseUtil.getConnection(); // Get database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepare SQL statement
            // Set parameters for the prepared statement
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getRollNo());
            stmt.setString(3, student.getDepartment());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPhone());
            stmt.setFloat(6, student.getMarks());
            return stmt.executeUpdate() > 0; // Execute update and return success status
        } catch (SQLException e) {
            System.out.println("Error adding student: " + e.getMessage()); // Handle exceptions
            return false;
        }
    }

    // Method to retrieve all students from the database
    public List<Student> getAllStudents() {
        List<Student> list = new ArrayList<>(); // List to hold students
        String sql = "SELECT * FROM students";
        try (Connection conn = DatabaseUtil.getConnection(); // Get database connection
             Statement stmt = conn.createStatement(); // Create statement
             ResultSet rs = stmt.executeQuery(sql)) { // Execute query
            // Iterate through result set and create Student objects
            while (rs.next()) {
                list.add(new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getFloat("marks")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Error fetching students: " + e.getMessage()); // Handle exceptions
        }
        return list; // Return list of students
    }

    // Method to search for a student by roll number or name
    public Student searchByRollOrName(String key) {
        String sql = "SELECT * FROM students WHERE roll_no = ? OR name LIKE ? LIMIT 1";
        try (Connection conn = DatabaseUtil.getConnection(); // Get database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepare SQL statement
            // Set parameters for the prepared statement
            stmt.setString(1, key);
            stmt.setString(2, "%" + key + "%");
            ResultSet rs = stmt.executeQuery(); // Execute query
            if (rs.next()) { // Check if a student is found
                return new Student(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("roll_no"),
                        rs.getString("department"),
                        rs.getString("email"),
                        rs.getString("phone"),
                        rs.getFloat("marks")
                );
            }
        } catch (SQLException e) {
            System.out.println("Error searching student: " + e.getMessage()); // Handle exceptions
        }
        return null; // Return null if no student is found
    }

    // Method to update an existing student's details
    public boolean updateStudent(Student student) {
        String sql = "UPDATE students SET name=?, roll_no=?, department=?, email=?, phone=?, marks=? WHERE id=?";
        try (Connection conn = DatabaseUtil.getConnection(); // Get database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepare SQL statement
            // Set parameters for the prepared statement
            stmt.setString(1, student.getName());
            stmt.setString(2, student.getRollNo());
            stmt.setString(3, student.getDepartment());
            stmt.setString(4, student.getEmail());
            stmt.setString(5, student.getPhone());
            stmt.setFloat(6, student.getMarks());
            stmt.setInt(7, student.getId());
            return stmt.executeUpdate() > 0; // Execute update and return success status
        } catch (SQLException e) {
            System.out.println("Error updating student: " + e.getMessage()); // Handle exceptions
            return false;
        }
    }

    // Method to search for students by roll number, name, or email
    public List<Student> searchByRollNameOrEmail(String keyword) {
        List<Student> students = new ArrayList<>(); // List to hold found students
        String sql = "SELECT * FROM students WHERE roll_no LIKE ? OR name LIKE ? OR email LIKE ?";
        try (Connection conn = DatabaseUtil.getConnection(); // Get database connection
             PreparedStatement pstmt = conn.prepareStatement(sql)) { // Prepare SQL statement
            String likeKeyword = "%" + keyword + "%"; // Prepare wildcard search
            pstmt.setString(1, likeKeyword);
            pstmt.setString(2, likeKeyword);
            pstmt.setString(3, likeKeyword);

            try (ResultSet rs = pstmt.executeQuery()) { // Execute query
                // Map each result to a Student object
                while (rs.next()) {
                    students.add(mapResultSetToStudent(rs));
                }
            }
        } catch (SQLException e) {
            e.printStackTrace(); // Handle exceptions
        }
        return students; // Return list of found students
    }

    // Helper method to map ResultSet to Student object
    private Student mapResultSetToStudent(ResultSet rs) throws SQLException {
        Student student = new Student(); // Create new Student object
        student.setId(rs.getInt("id"));
        student.setName(rs.getString("name"));
        student.setRollNo(rs.getString("roll_no"));
        student.setDepartment(rs.getString("department"));
        student.setEmail(rs.getString("email"));
        student.setPhone(rs.getString("phone"));
        student.setMarks(rs.getFloat("marks"));
        return student; // Return mapped Student object
    }

    // Method to delete a student by ID
    public boolean deleteStudent(int id) {
        String sql = "DELETE FROM students WHERE id=?";
        try (Connection conn = DatabaseUtil.getConnection(); // Get database connection
             PreparedStatement stmt = conn.prepareStatement(sql)) { // Prepare SQL statement
            stmt.setInt(1, id); // Set student ID for deletion
            return stmt.executeUpdate() > 0; // Execute update and return success status
        } catch (SQLException e) {
            System.out.println("Error deleting student: " + e.getMessage()); // Handle exceptions
            return false;
        }
    }

}