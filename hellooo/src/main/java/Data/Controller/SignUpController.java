package Data.Controller;

import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.sql.Connection;
import java.sql.SQLException;

import Data.Model.Host;
import Data.Model.Tenant;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import Data.Model.Person;
import Data.Model.Owner;
import Data.DAO.PersonDAO;
import Data.DAO.OwnerDAO;
import Data.DAO.HostDAO;
import Data.DAO.TenantDAO;
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
    private TextField genderTextField;

    @FXML
    private Label infoLabel;

    @FXML
    private TextField fullNameTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private ComboBox<String> Role;

    @FXML
    private void initialize() {
        Role.getItems().addAll("Host", "Tenant", "Owner", "Manager");
    }

    @FXML
    private void handleAddAction(ActionEvent event) {
        // Retrieve values from the sign-up form
        String name = nameTextField.getText().trim();
        String gender = genderTextField.getText().trim();
        String fullname = fullNameTextField.getText().trim();
        LocalDate localDate = birthdayDatePicker.getValue();
        int phonenumber = Integer.parseInt(phoneNumberTextField.getText());
        String email = emailTextField.getText();
        String roleSelected = Role.getValue();

        if (name.isEmpty() || gender.isEmpty() || localDate == null || roleSelected == null) {
            infoLabel.setText("Please fill in all fields.");
            return;
        }

        // Validate password and confirmation
        if (!password.getText().equals(reenterPassword.getText())) {
            infoLabel.setText("Passwords do not match.");
            return;
        }

        // Convert LocalDate to java.util.Date
        Date birthday = Date.from(localDate.atStartOfDay(ZoneId.systemDefault()).toInstant());

        // Connect to the database
        Connection connection = DatabaseConnection.connect();
        if (connection == null) {
            infoLabel.setText("Database connection failed.");
            return;
        }

        try {
            // Create a Person record
            PersonDAO personDAO = new PersonDAO(connection);
            Person person = new Person();
            person.setUsername(name);
            person.setPassword(password.getText());
            personDAO.create(person);

            // Create specific records based on role
            if ("Owner".equalsIgnoreCase(roleSelected)) {
                OwnerDAO ownerDAO = new OwnerDAO(connection);
                Owner owner = new Owner();
                owner.setUsername(name);
                owner.setPassword(password.getText());
                owner.setFullname(fullname);
                owner.setGender(gender);
                owner.setDoB(birthday);
                owner.setPhone_number(phonenumber);
                owner.setEmail(email);
                ownerDAO.create(owner);

                infoLabel.setText("Successfully added Owner: " + name);
            } else if ("Host".equalsIgnoreCase(roleSelected)) {
                HostDAO hostDAO = new HostDAO(connection);
                Host host = new Host();
                host.setUsername(name);
                host.setPassword(password.getText());
                host.setFullname(fullname);
                host.setGender(gender);
                host.setDoB(birthday);
                host.setPhone_number(phonenumber);
                host.setEmail(email);
                hostDAO.create(host);

                infoLabel.setText("Successfully added Host: " + name);
            } else if ("Tenant".equalsIgnoreCase(roleSelected)) {
                TenantDAO tenantDAO = new TenantDAO(connection);
                Tenant tenant = new Tenant();
                tenant.setUsername(name);
                tenant.setPassword(password.getText());
                tenant.setFullname(fullname);
                tenant.setGender(gender);
                tenant.setDoB(birthday);
                tenant.setPhone_number(phonenumber);
                tenant.setEmail(email);
                tenantDAO.create(tenant);

                infoLabel.setText("Successfully added Tenant: " + name);
            }
        } catch (RuntimeException ex) {
            infoLabel.setText("Error adding profile: " + ex.getMessage());
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