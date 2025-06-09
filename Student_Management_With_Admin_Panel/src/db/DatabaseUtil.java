package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseUtil {
    // Database connection details
    private static final String DB_URL = "jdbc:mysql://localhost:3306/smartstudentsdb"; // Database URL
    private static final String DB_USER = "root"; // Database username
    private static final String DB_PASSWORD = "Mysql@145"; // Database password

    // Method to establish and return a connection to the database
    public static Connection getConnection() throws SQLException {
        // Attempt to get a connection to the database using the provided URL, username, and password
        return DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
    }
}
