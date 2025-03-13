module com.example.hellooo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens Data.Run to javafx.fxml;
    exports Data.Run;
    exports Data.Controller;
    opens Data.Controller to javafx.fxml;
}