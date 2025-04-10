package Data.DAO;

import Data.Model.Property;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class PropertyDAO implements DAO<Property> {

    private final Connection connection;

    public PropertyDAO(Connection connection)
    {
        this.connection = connection;
    }

    @Override
    public void create(Property entity) {
            String query = "INSERT INTO \"Property\" (price, address) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = connection.prepareStatement(query)) {
                stmt.setDouble(1, entity.getPrice());
                stmt.setString(2, entity.getAddress());
                stmt.executeUpdate();
                System.out.println("Add successfully !");
            } catch (SQLException e)
            {
                throw new RuntimeException("Error inserting Person record", e);
            }
        }

}
