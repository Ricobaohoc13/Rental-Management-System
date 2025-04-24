package Data.Controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.stage.Stage;

import java.io.IOError;
import java.io.IOException;
import java.net.URL;



public class MainScreenController {
    @FXML
    private void Return(ActionEvent event)
    {
        try{
            URL fxmlURL = getClass().getResource("/WelcomeScreen.fxml");
            if (fxmlURL == null)
            {
                throw new IOException("bruhhhhh");
            }
            Parent root = FXMLLoader.load(fxmlURL);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
