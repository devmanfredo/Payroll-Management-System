module com.example.payrollsystem {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;



    opens com.example.payrollsystem to javafx.fxml;
    exports com.example.payrollsystem;
}