package com.example.payrollsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class RegistrationController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleCombo;
    @FXML
    private Label statusLabel;

    @FXML
    private void initialize() {
        roleCombo.getItems().addAll("Admin", "Employee");
    }

    @FXML
    private void registerUser() {
        String username = usernameField.getText().trim();
        String password = passwordField.getText().trim();
        String role = roleCombo.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        try {
            if (DatabaseHandler.getInstance().registerUser(username, password, role)) {
                statusLabel.setText("Registration successful! Redirecting to login...");
                // Automatically redirect to login after successful registration
                redirectToLogin();
            } else {
                statusLabel.setText("Registration failed. Username may already exist.");
            }
        } catch (SQLException e) {
            statusLabel.setText("Error: " + e.getMessage());
        }
    }

    private void redirectToLogin() {
        try {
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/payrollsystem/login.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/payrollsystem/styles.css").toExternalForm());
            stage.setTitle("Payroll System - Login");
            stage.setScene(scene);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}