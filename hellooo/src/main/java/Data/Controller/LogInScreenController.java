package Data.Controller;

import Data.DAO.PersonDAO;
import Data.Link.DatabaseConnection;
import Data.Model.Person;
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
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

// Note: We assume that the FXML file actually assigns fx:id values to these nodes.
// The UI remains visually identical.
public class LogInScreenController {

    @FXML
    private TextField NameField;

    @FXML
    private PasswordField UserPasswordField;

    // Changed from Label to TextField because the FXML element for the verification code is a TextField.
    @FXML
    private TextField Verification;

    @FXML
    private Label infoLabel;

    @FXML
    private void LogIn(ActionEvent event) {
        // Get input values
        String name = NameField.getText().trim();
        String password = UserPasswordField.getText();
        String userInputVerification = Verification.getText();

        // Ensure all fields are filled
        if (name.isEmpty() || password.isEmpty() || userInputVerification.isEmpty()) {
            infoLabel.setText("Please fill in all the form");
            return;
        }

        // Connect to the database
        Connection connection = DatabaseConnection.connect();
        if (connection == null) {
            infoLabel.setText("Database connection failed");
            return;
        }

        PersonDAO personDAO = new PersonDAO(connection);
        try {
            // For this demo, we assume the correct verification code is "1234".
            // In a production app, you would generate the code dynamically and store it for comparison.
            if (!userInputVerification.equals("1234")) {
                infoLabel.setText("Incorrect verification code.");
                return;
            }

            // Validate username and password.
            // We assume PersonDAO has a method that checks credentials and returns a Person if found.
            Person user = personDAO.getUserByUsernameAndPassword(name, password);
            if (user == null) {
                infoLabel.setText("Invalid username or password.");
                return;
            }

            // Login is successful. Inform the user and transition to the next scene.
            infoLabel.setText("Login successful!");

            URL fxmlURL = getClass().getResource("/MainScreen.fxml");
            if (fxmlURL == null)
            {
                throw new IOException("BRUHHHHHHHH");
            }

            Parent root = FXMLLoader.load(fxmlURL);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
            infoLabel.setText("An error occurred while loading the next screen.");
        } catch (Exception e) {
            e.printStackTrace();
            infoLabel.setText("An error occurred during login.");
        }
    }

    @FXML
    private void GoBack(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/WelcomeScreen.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Can't locate path");
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            Stage stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}