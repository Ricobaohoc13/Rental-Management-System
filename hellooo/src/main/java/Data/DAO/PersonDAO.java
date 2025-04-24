package Data.DAO;

import Data.Model.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;

public class PersonDAO implements DAO<Person> {

    private final Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Person entity) {
        // If your schema requires case-sensitive names (as created with quotes), then use:
        String query = "INSERT INTO \"Person\" (username, \"Password\") VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Record inserted successfully.");
            } else {
                System.out.println("No record was inserted.");
            }
        } catch (SQLException e) {
            // Print stack trace for debugging; in production consider using a logging framework.
            e.printStackTrace();
            throw new RuntimeException("Error inserting Person record: " + e.getMessage(), e);
        }
    }

    public void delete(Person entity) {
        String query = "DELETE FROM \"Person\" WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.executeUpdate();
            System.out.println("Record deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Person record", e);
        }
    }

    public Person LogIn (String username, String password) {
        String query = "SELECT * FROM \"Person\" WHERE username = ? AND \"Password\" = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    // Assuming the Person class has a no-argument constructor and matching setters.
                    Person person = new Person();
                    person.setUsername(rs.getString("username"));
                    person.setPassword(rs.getString("Password"));
                    // Convert the SQL Date to java.util.Date if needed.
                    return person;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving Person record: " + e.getMessage(), e);
        }
        return null;
    }
}