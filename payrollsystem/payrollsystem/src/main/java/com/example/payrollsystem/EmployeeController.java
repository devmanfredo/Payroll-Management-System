package com.example.payrollsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.util.converter.DoubleStringConverter;
import javafx.util.converter.IntegerStringConverter;

import java.io.IOException;
import java.sql.SQLException;

public class EmployeeController {
    @FXML
    private TextField employeeIdField, nameField, departmentField, positionField, salaryField, hoursField;
    @FXML
    private Label statusLabel;
    @FXML
    private Button addButton, updateButton, deleteButton;

    @FXML
    private void initialize() {
        //input validation
        setupNumericField(employeeIdField);
        setupDecimalField(salaryField);
        setupDecimalField(hoursField);

        if (LoginController.getLoggedInRole().equals("Employee")) {
            addButton.setDisable(true);
            updateButton.setDisable(true);
            deleteButton.setDisable(true);
        }
    }

    private void setupNumericField(TextField field) {
        field.setTextFormatter(new TextFormatter<>(new IntegerStringConverter(), 0, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*")) {
                return change;
            }
            return null;
        }));
    }

    private void setupDecimalField(TextField field) {
        field.setTextFormatter(new TextFormatter<>(new DoubleStringConverter(), 0.0, change -> {
            String newText = change.getControlNewText();
            if (newText.matches("\\d*\\.?\\d*")) {
                return change;
            }
            return null;
        }));
    }

    @FXML
    private void addEmployee() {
        try {
            Employee employee = createEmployeeFromFields();
            if (employee != null) {
                DatabaseHandler.getInstance().addEmployee(employee);
                statusLabel.setText("Employee added successfully!");
                clearFields();
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error adding employee: " + e.getMessage());
        }
    }

    @FXML
    private void updateEmployee() {
        try {
            Employee employee = createEmployeeFromFields();
            if (employee != null) {
                DatabaseHandler.getInstance().updateEmployee(employee);
                statusLabel.setText("Employee updated successfully!");
            }
        } catch (SQLException e) {
            showAlert("Database Error", "Error updating employee: " + e.getMessage());
        }
    }

    @FXML
    private void deleteEmployee() {
        try {
            String idText = employeeIdField.getText().trim();
            if (idText.isEmpty()) {
                showAlert("Input Error", "Employee ID cannot be empty!");
                return;
            }

            int id = Integer.parseInt(idText);
            DatabaseHandler.getInstance().deleteEmployee(id);
            statusLabel.setText("Employee deleted successfully!");
            clearFields();
        } catch (SQLException e) {
            showAlert("Database Error", "Error deleting employee: " + e.getMessage());
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter a valid Employee ID");
        }
    }

    @FXML
    private void goToSalary() throws IOException {
        loadScene("salary.fxml");
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

    private Employee createEmployeeFromFields() {
        try {
            // Validate ID
            String idText = employeeIdField.getText().trim();
            if (idText.isEmpty()) {
                showAlert("Input Error", "Employee ID cannot be empty!");
                return null;
            }
            int id = Integer.parseInt(idText);

            // Validate name
            String name = nameField.getText().trim();
            if (name.isEmpty()) {
                showAlert("Input Error", "Name cannot be empty!");
                return null;
            }

            // Validate department
            String department = departmentField.getText().trim();
            if (department.isEmpty()) {
                showAlert("Input Error", "Department cannot be empty!");
                return null;
            }

            // Validate position
            String position = positionField.getText().trim();
            if (position.isEmpty()) {
                showAlert("Input Error", "Position cannot be empty!");
                return null;
            }

            // Validate salary
            String salaryText = salaryField.getText().trim();
            if (salaryText.isEmpty()) {
                showAlert("Input Error", "Salary cannot be empty!");
                return null;
            }
            double salary = Double.parseDouble(salaryText);
            if (salary < 0) {
                showAlert("Input Error", "Salary cannot be negative!");
                return null;
            }

            // Validate hours
            String hoursText = hoursField.getText().trim();
            if (hoursText.isEmpty()) {
                showAlert("Input Error", "Hours cannot be empty!");
                return null;
            }
            double hours = Double.parseDouble(hoursText);
            if (hours < 0) {
                showAlert("Input Error", "Hours cannot be negative!");
                return null;
            }

            return new Employee(id, name, department, position, salary, hours);
        } catch (NumberFormatException e) {
            showAlert("Input Error", "Please enter valid numbers for ID, Salary, and Hours");
            return null;
        }
    }

    private void clearFields() {
        employeeIdField.clear();
        nameField.clear();
        departmentField.clear();
        positionField.clear();
        salaryField.clear();
        hoursField.clear();
    }

    private void loadScene(String fxml) throws IOException {
        Stage stage = (Stage) employeeIdField.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}