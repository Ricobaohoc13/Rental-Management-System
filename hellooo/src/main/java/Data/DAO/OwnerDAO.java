package Data.DAO;

import Data.Model.Owner;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;

public class OwnerDAO implements DAO<Owner> {
    private final Connection connection;

    public OwnerDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Owner entity) {
        String query = "INSERT INTO \"Owner\" (username, \"Password\", fullname, gender, BOD, \"phone no\", email) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.setString(2, entity.getPassword());
            stmt.setString(3, entity.getFullname());
            stmt.setString(4, entity.getGender());
            stmt.setDate(5, new Date(entity.getDoB().getTime()));
            stmt.setInt(6, entity.getPhone_number());
            stmt.setString(7, entity.getEmail());

            int affectedRows = stmt.executeUpdate();
            if (affectedRows > 0) {
                System.out.println("Owner record inserted successfully.");
            } else {
                System.out.println("No owner record was inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting Owner record: " + e.getMessage(), e);
        }
    }

    public void delete(Owner entity) {
        String query = "DELETE FROM \"Owner\" WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.executeUpdate();
            System.out.println("Owner record deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Owner record", e);
        }
    }

    public Owner logIn(String username, String password) {
        String query = "SELECT * FROM \"Owner\" WHERE username = ? AND \"Password\" = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Owner owner = new Owner();
                    owner.setUsername(rs.getString("username"));
                    owner.setPassword(rs.getString("Password"));
                    owner.setFullname(rs.getString("fullname"));
                    owner.setGender(rs.getString("gender"));
                    owner.setDoB(new java.util.Date(rs.getDate("bod").getTime()));
                    owner.setPhone_number(rs.getInt("phone no"));
                    owner.setEmail(rs.getString("email"));
                    return owner;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving Owner record: " + e.getMessage(), e);
        }
        return null;
    }
}
