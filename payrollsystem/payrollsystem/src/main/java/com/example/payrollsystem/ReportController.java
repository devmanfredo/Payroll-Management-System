package com.example.payrollsystem;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.XYChart;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ReportController {
    @FXML
    private BarChart<String, Number> salaryChart;

    @FXML
    private void generateReport() {
        try {
            XYChart.Series<String, Number> series = new XYChart.Series<>();
            series.setName("Salaries");
            ResultSet rs = DatabaseHandler.getInstance().getAllEmployees();
            while (rs.next()) {
                series.getData().add(new XYChart.Data<>(rs.getString("name"), rs.getDouble("basic_salary")));
            }
            salaryChart.getData().clear();
            salaryChart.getData().add(series);
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
    private void goToPayslip() throws IOException {
        loadScene("payslip.fxml");
    }

    @FXML
    private void logout() throws IOException {
        loadScene("login.fxml");
    }

    private void loadScene(String fxml) throws IOException {
        Stage stage = (Stage) salaryChart.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getResource(fxml));
        Scene scene = new Scene(root);
        scene.getStylesheets().add(getClass().getResource("styles.css").toExternalForm());
        stage.setScene(scene);
    }
}