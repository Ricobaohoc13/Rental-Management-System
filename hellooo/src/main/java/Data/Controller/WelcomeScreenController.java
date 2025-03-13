package Data.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;

public class WelcomeScreenController {

    @FXML
    private void LogIn(ActionEvent event) {

    }

    @FXML
    private void SignUp(ActionEvent event) {
        try {
            // Get the resource for the FXML file
            URL fxmlUrl = getClass().getResource("/AddNewPerson.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot locate AddNewPerson.fxml. Check the resource path.");
            }

            // Load the FXML and create a new root node
            Parent root = FXMLLoader.load(fxmlUrl);

            // Retrieve the current stage and switch the scene
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void Exit(ActionEvent event) {
        System.exit(0);
    }
}
