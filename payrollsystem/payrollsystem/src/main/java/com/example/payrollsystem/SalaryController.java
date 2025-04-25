package com.example.payrollsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SalaryController {
    @FXML
    private TextField employeeIdField, overtimeField, deductionsField;
    @FXML
    private Label resultLabel;

    @FXML
    private void calculateSalary() {
        try {
            // Validate inputs
            if (employeeIdField.getText().isEmpty() ||
                    overtimeField.getText().isEmpty() ||
                    deductionsField.getText().isEmpty()) {
                resultLabel.setText("Please fill all fields");
                return;
            }

            int id = Integer.parseInt(employeeIdField.getText());
            double overtimePay = Double.parseDouble(overtimeField.getText());
            double deductions = Double.parseDouble(deductionsField.getText());

            ResultSet rs = DatabaseHandler.getInstance().getEmployee(id);
            if (rs.next()) {
                double basicSalary = rs.getDouble("basic_salary");
                double workingHours = rs.getDouble("working_hours");
                double grossSalary = basicSalary + (workingHours * overtimePay);
                double netSalary = grossSalary - deductions;
                resultLabel.setText("Net Salary: $" + String.format("%.2f", netSalary));
            } else {
                resultLabel.setText("Employee not found.");
            }
        } catch (NumberFormatException e) {
            resultLabel.setText("Please enter valid numbers");
        } catch (SQLException e) {
            resultLabel.setText("Database error: " + e.getMessage());
        }
    }

    @FXML
    private void goToEmployee() throws IOException {
        loadScene("employee.fxml");
    }

    @FXML
    private void goToPayslip() throws IOException {
        loadScene("payslip.fxml");
    }

    @FXML
    private void goToReports() throws IOException {
        loadScene("report.fxml");
    }

    @FXML
    private void logout() throws IOException {
        loadScene("login.fxml");
    }

    private void loadScene(String fxml) throws IOException {
        Stage stage = (Stage) employeeIdField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
    }
}