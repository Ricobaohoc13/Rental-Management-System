module com.example.hellooo {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires javafx.graphics;


    opens com.example.hellooo to javafx.fxml;
    exports com.example.hellooo;
    exports Controller;
    opens Controller to javafx.fxml;
}