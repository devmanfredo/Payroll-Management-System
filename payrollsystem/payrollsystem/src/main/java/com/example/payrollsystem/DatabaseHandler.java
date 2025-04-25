package com.example.payrollsystem;

import java.sql.*;

public class DatabaseHandler {
    private static DatabaseHandler handler;
    private Connection connection;

    private DatabaseHandler() {
        createConnection();
        createTables();
    }

    public static DatabaseHandler getInstance() {
        if (handler == null) {
            handler = new DatabaseHandler();
        }
        return handler;
    }

    private void createConnection() {
        try {
            connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/payroll_db",
                    "root",
                    "123456"
            );
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void createTables() {
        try {
            Statement stmt = connection.createStatement();

            // Create users table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS users (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY, " +
                    "username VARCHAR(50) UNIQUE NOT NULL, " +
                    "password VARCHAR(100) NOT NULL, " +
                    "role VARCHAR(20) NOT NULL)");

            // Create employees table
            stmt.executeUpdate("CREATE TABLE IF NOT EXISTS employees (" +
                    "id INT PRIMARY KEY, " +
                    "name VARCHAR(100) NOT NULL, " +
                    "department VARCHAR(50) NOT NULL, " +
                    "position VARCHAR(50) NOT NULL, " +
                    "basic_salary DOUBLE NOT NULL, " +
                    "working_hours DOUBLE NOT NULL)");

            stmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean registerUser(String username, String password, String role) throws SQLException {
        String query = "INSERT INTO users (username, password, role) VALUES (?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean validateUser(String username, String password, String role) throws SQLException {
        String query = "SELECT * FROM users WHERE username = ? AND password = ? AND role = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, username);
            pstmt.setString(2, password);
            pstmt.setString(3, role);
            ResultSet rs = pstmt.executeQuery();
            return rs.next();
        }
    }

    public ResultSet getEmployee(int id) throws SQLException {
        String query = "SELECT * FROM employees WHERE id = ?";
        PreparedStatement pstmt = connection.prepareStatement(query);
        pstmt.setInt(1, id);
        return pstmt.executeQuery();
    }

    public ResultSet getAllEmployees() throws SQLException {
        String query = "SELECT * FROM employees";
        Statement stmt = connection.createStatement();
        return stmt.executeQuery(query);
    }

    public boolean addEmployee(Employee employee) throws SQLException {
        String query = "INSERT INTO employees (id, name, department, position, basic_salary, working_hours) " +
                "VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, employee.getId());
            pstmt.setString(2, employee.getName());
            pstmt.setString(3, employee.getDepartment());
            pstmt.setString(4, employee.getPosition());
            pstmt.setDouble(5, employee.getBasicSalary());
            pstmt.setDouble(6, employee.getWorkingHours());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean updateEmployee(Employee employee) throws SQLException {
        String query = "UPDATE employees SET name = ?, department = ?, position = ?, " +
                "basic_salary = ?, working_hours = ? WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setString(1, employee.getName());
            pstmt.setString(2, employee.getDepartment());
            pstmt.setString(3, employee.getPosition());
            pstmt.setDouble(4, employee.getBasicSalary());
            pstmt.setDouble(5, employee.getWorkingHours());
            pstmt.setInt(6, employee.getId());
            return pstmt.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployee(int id) throws SQLException {
        String query = "DELETE FROM employees WHERE id = ?";
        try (PreparedStatement pstmt = connection.prepareStatement(query)) {
            pstmt.setInt(1, id);
            return pstmt.executeUpdate() > 0;
        }
    }
}