package DAO;

import Model.Person;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;

public class PersonDAO implements DAO<Person> {

    private final Connection connection;

    public PersonDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Person entity) {
        String query = "INSERT INTO \"Person\" (username, birthday, gender) VALUES (?,?,?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            // Convert java.util.Date to java.sql.Date
            stmt.setDate(2, new Date(entity.getBirthday().getTime()));
            stmt.setString(3, entity.getGender());
            stmt.executeUpdate();
            System.out.println("Record inserted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error inserting Person record", e);
        }
    }
}
