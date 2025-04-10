package Data.Controller;

import Data.DAO.PersonDAO;
import Data.Link.DatabaseConnection;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.w3c.dom.Text;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.util.Date;

public class LogInScreenController {
    @FXML
    private TextField NameField;

    @FXML
    private PasswordField UserPasswordField;

    @FXML
    private Label Verification;
    @FXML
    private Label infoLabel;

    @FXML
    private void LogIn(ActionEvent event) {
        String name = NameField.getText().trim();
        String password = UserPasswordField.getText();
        String verification = Verification.getText();

        if (name.isEmpty() || password.isEmpty() || verification.isEmpty())
        {
            infoLabel.setText("Please fill in all the form");
            return;
        }

        Connection connection = DatabaseConnection.connect();
        if (connection == null) {
            infoLabel.setText("Database connection failed");
        }
        PersonDAO personDAO = new PersonDAO(connection);
        try{

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    private void GoBack(ActionEvent event)
    {
        try {
            URL fxmlUrl = getClass().getResource("/WelcomeScreen.fxml");
            if (fxmlUrl == null)
            {
                throw new IOException("Can`t locate path");
            }
            Parent root = FXMLLoader.load(fxmlUrl);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        }catch (IOException e)
        {
            e.printStackTrace();
        }
    }
}
