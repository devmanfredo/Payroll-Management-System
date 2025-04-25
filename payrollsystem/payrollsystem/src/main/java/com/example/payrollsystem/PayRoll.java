package com.example.payrollsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PayRoll extends Application {
    @Override
    public void start(Stage primaryStage) throws Exception {
        // Start with registration screen first
        Parent root = FXMLLoader.load(getClass().getResource("/com/example/payrollsystem/register.fxml"));
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/com/example/payrollsystem/styles.css").toExternalForm());
        primaryStage.setTitle("Payroll System - Registration");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}