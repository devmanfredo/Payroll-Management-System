package com.example.payrollsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PayslipController {
    @FXML
    private TextField employeeIdField;
    @FXML
    private TextArea payslipArea;

    @FXML
    private void generatePayslip() {
        try {
            int id = Integer.parseInt(employeeIdField.getText());
            ResultSet rs = DatabaseHandler.getInstance().getEmployee(id);
            if (rs.next()) {
                String payslip = "=== Payslip ===\n" +
                        "Employee ID: " + rs.getInt("id") + "\n" +
                        "Name: " + rs.getString("name") + "\n" +
                        "Department: " + rs.getString("department") + "\n" +
                        "Position: " + rs.getString("position") + "\n" +
                        "Basic Salary: $" + rs.getDouble("basic_salary") + "\n" +
                        "Working Hours: " + rs.getDouble("working_hours");
                payslipArea.setText(payslip);
            } else {
                payslipArea.setText("Employee not found.");
            }
        } catch (SQLException e) {
            payslipArea.setText("Error: " + e.getMessage());
        }
    }

    @FXML
    private void exportToCSV() {
        try (PrintWriter pw = new PrintWriter(new File("payslip.csv"))) {
            pw.write(payslipArea.getText().replace("\n", ","));
            payslipArea.setText(payslipArea.getText() + "\nExported to CSV!");
        } catch (FileNotFoundException e) {
            payslipArea.setText("Error exporting to CSV: " + e.getMessage());
        }
    }

    @FXML
    private void exportToPDF() {

        payslipArea.setText(payslipArea.getText() + "\nPDF export not implemented.");
    }

    @FXML
    private void goToEmployee() throws IOException {
        loadScene("employee.fxml");
    }

    @FXML
    private void goToSalary() throws IOException {
        loadScene("salary.fxml");
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