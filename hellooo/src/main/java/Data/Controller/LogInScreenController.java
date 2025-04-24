package Data.Controller;

import Data.DAO.OwnerDAO;
import Data.DAO.HostDAO;
import Data.DAO.TenantDAO;
import Data.Link.DatabaseConnection;
import Data.Model.Owner;
import Data.Model.Host;
import Data.Model.Tenant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;

public class LogInScreenController {

    @FXML
    private TextField NameField;

    @FXML
    private PasswordField UserPasswordField;

    @FXML
    private TextField Verification;

    @FXML
    private ComboBox<String> Role;

    @FXML
    private Label infoLabel;

    @FXML
    private void initialize() {
        // Populate ComboBox with role options
        Role.getItems().addAll("Host", "Tenant", "Owner");
    }

    @FXML
    private void LogIn(ActionEvent event) {
        // Retrieve input values
        String name = NameField.getText().trim();
        String password = UserPasswordField.getText();
        String userInputVerification = Verification.getText();
        String roleSelected = Role.getValue();

        // Check that all fields have been filled in
        if (name.isEmpty() || password.isEmpty() || userInputVerification.isEmpty() || roleSelected == null) {
            infoLabel.setText("Please fill in all the fields.");
            return;
        }

        // Verification code check (adjust as needed)
        if (!userInputVerification.equals("1234")) {
            infoLabel.setText("Incorrect verification code.");
            return;
        }

        // Connect to the database
        Connection connection = DatabaseConnection.connect();
        if (connection == null) {
            infoLabel.setText("Database connection failed.");
            return;
        }

        try {
            // Determine which DAO to use based on role
            Object user = null;
            if ("Owner".equalsIgnoreCase(roleSelected)) {
                OwnerDAO ownerDAO = new OwnerDAO(connection);
                user = ownerDAO.logIn(name, password);
            } else if ("Host".equalsIgnoreCase(roleSelected)) {
                HostDAO hostDAO = new HostDAO(connection);
                user = hostDAO.logIn(name, password);
            } else if ("Tenant".equalsIgnoreCase(roleSelected)) {
                TenantDAO tenantDAO = new TenantDAO(connection);
                user = tenantDAO.logIn(name, password);
            }

            // Handle login success or failure
            if (user == null) {
                infoLabel.setText("Invalid username or password.");
                return;
            }

            infoLabel.setText("Login successful!");

            // Load the MainScreen.fxml after successful login
            URL fxmlURL = getClass().getResource("/MainScreen.fxml");
            if (fxmlURL == null) {
                throw new IOException("Cannot locate next screen FXML.");
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
        } finally {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    private void GoBack(ActionEvent event) {
        try {
            URL fxmlUrl = getClass().getResource("/WelcomeScreen.fxml");
            if (fxmlUrl == null) {
                throw new IOException("Cannot locate welcome screen FXML.");
            }
            Parent root = FXMLLoader.load(fxmlUrl);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}