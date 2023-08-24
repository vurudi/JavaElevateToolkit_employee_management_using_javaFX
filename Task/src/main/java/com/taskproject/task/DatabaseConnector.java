package com.taskproject.task;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * The DatabaseConnector class provides methods to establish a connection with the database.
 */
public class DatabaseConnector {
    private static final String URL = "jdbc:mysql://localhost:3306/data_source1";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "vurudi100@gmail.com";

    /**
     * Establishes a connection to the database.
     *
     * @return the database connection
     * @throws SQLException if a database access error occurs
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }

    /**
     * The main method for testing the database connection.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            getConnection();
            System.out.println("Connection successful");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
