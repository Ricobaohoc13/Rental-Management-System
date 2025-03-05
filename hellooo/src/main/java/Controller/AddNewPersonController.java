package Controller;

import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import Model.Person;
import DAO.PersonDAO;
import Link.DatabaseConnection;
import java.sql.Connection;
import java.sql.SQLException;

public class AddNewPersonController implements Initializable {

    @FXML
    private Button addButton;

    @FXML
    private TextField nameTextField;

    @FXML
    private DatePicker birthdayDatePicker;

    @FXML
    private TextField genderTextField;

    @FXML
    private Label infoLabel;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // You could initialize things here if needed.
    }

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

        // Create a new Person instance.
        Person newPerson = new Person();
        newPerson.setUsername(name);
        newPerson.setGender(gender);
        newPerson.setBirthday(birthday);

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
}
