package Data.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.sql.Connection;
import java.sql.SQLException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import Data.Model.Person;
import Data.DAO.PersonDAO;
import Data.Link.DatabaseConnection;

public class SignUpController {

    @FXML
    private Button addButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private PasswordField password;

    @FXML
    private PasswordField reenterPassword;

    @FXML
    private TextField verification;

    @FXML
    private TextField genderTextField;

    @FXML
    private Label infoLabel;

    /**
     * Event handler for the Add button.
     */
    @FXML
    private void handleAddAction(ActionEvent event) {
        // Retrieve and validate data entered by the user.
        String name = nameTextField.getText().trim();
        String gender = genderTextField.getText().trim();
        LocalDate localDate = birthdayDatePicker.getValue();

        if (name.isEmpty() || gender.isEmpty() || localDate == null) {
            infoLabel.setText("Please fill in all fields.");
            return;
        }

        // Convert LocalDate to java.util.Date.
        Date birthday = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Create a new Person instance and set its fields.
        Person newPerson = new Person();
        newPerson.setUsername(name);
        newPerson.setPassword(password.getText());
        newPerson.setBirthday(birthday);
        newPerson.setGender(gender);

        // Connect to the database and attempt to insert the record.
        Connection connection = DatabaseConnection.connect();
        if (connection == null) {
            infoLabel.setText("Database connection failed.");
            return;
        }
        PersonDAO personDAO = new PersonDAO(connection);
        try {
            personDAO.create(newPerson);
            infoLabel.setText("Successfully added person: " + name);
        } catch (RuntimeException ex) {
            infoLabel.setText("Error adding person: " + ex.getMessage());
        } finally {
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
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
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}