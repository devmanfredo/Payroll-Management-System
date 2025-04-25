package com.example.payrollsystem;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

public class LoginController implements Initializable {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private ComboBox<String> roleCombo;
    @FXML
    private Label statusLabel;

    private static String loggedInRole;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        roleCombo.setItems(FXCollections.observableArrayList("Admin", "Employee"));
    }

    @FXML
    private void login() throws IOException, SQLException {
        String username = usernameField.getText();
        String password = passwordField.getText();
        String role = roleCombo.getValue();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setText("Please fill all fields.");
            return;
        }

        DatabaseHandler db = DatabaseHandler.getInstance();
        if (db.validateUser(username, password, role)) {
            loggedInRole = role;
            Stage stage = (Stage) usernameField.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getResource("/com/example/payrollsystem/employee.fxml"));
            Scene scene = new Scene(root);
            scene.getStylesheets().add(getClass().getResource("/com/example/payrollsystem/styles.css").toExternalForm());
            stage.setTitle("Payroll System - Employee Management");
            stage.setScene(scene);
        } else {
            statusLabel.setText("Invalid credentials or role.");
        }
    }

    public static String getLoggedInRole() {
        return loggedInRole;
    }
}