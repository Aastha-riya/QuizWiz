package com.quizwiz.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Database connection utility class for QuizWiz application.
 * Provides JDBC connection to MySQL database with proper exception handling.
 */
public class DBConnection {
    
    // Database configuration constants
    private static final String DB_URL = "jdbc:mysql://localhost:3306/quizwiz_db";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "password";
    private static final String DB_DRIVER = "com.mysql.cj.jdbc.Driver";
    
    /**
     * Establishes and returns a connection to the MySQL database.
     * 
     * @return Connection object to quizwiz_db database
     * @throws SQLException if database connection fails
     */
    public static Connection getConnection() throws SQLException {
        Connection connection = null;
        
        try {
            // Load MySQL JDBC driver
            Class.forName(DB_DRIVER);
            
            // Establish connection
            connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            
            if (connection != null) {
                System.out.println("Database connection established successfully.");
            }
            
        } catch (ClassNotFoundException e) {
            System.err.println("MySQL JDBC Driver not found.");
            e.printStackTrace();
            throw new SQLException("Database driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.err.println("Failed to establish database connection.");
            e.printStackTrace();
            throw e;
        }
        
        return connection;
    }
    
    /**
     * Closes the database connection safely.
     * 
     * @param connection Connection object to close
     */
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
                System.out.println("Database connection closed successfully.");
            } catch (SQLException e) {
                System.err.println("Error closing database connection.");
                e.printStackTrace();
            }
        }
    }
}
