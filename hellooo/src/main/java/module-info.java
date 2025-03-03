module com.example.hellooo {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.hellooo to javafx.fxml;
    exports com.example.hellooo;
}