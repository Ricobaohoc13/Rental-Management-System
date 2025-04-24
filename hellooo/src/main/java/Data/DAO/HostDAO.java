package Data.DAO;

import Data.Model.Host;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Date;
import java.sql.ResultSet;

public class HostDAO implements DAO<Host> {
    private final Connection connection;

    public HostDAO(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void create(Host entity) {
        String query = "INSERT INTO \"Host\" (username, \"Password\", fullname, gender, BOD, \"phone no\", email) VALUES (?, ?, ?, ?, ?, ?, ?)";
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
                System.out.println("Host record inserted successfully.");
            } else {
                System.out.println("No Host record was inserted.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error inserting Host record: " + e.getMessage(), e);
        }
    }

    public void delete(Host entity) {
        String query = "DELETE FROM \"Host\" WHERE username = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, entity.getUsername());
            stmt.executeUpdate();
            System.out.println("Host record deleted successfully.");
        } catch (SQLException e) {
            throw new RuntimeException("Error deleting Host record", e);
        }
    }

    public Host logIn(String username, String password) {
        String query = "SELECT * FROM \"Host\" WHERE username = ? AND \"Password\" = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, username);
            stmt.setString(2, password);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    Host Host = new Host();
                    Host.setUsername(rs.getString("username"));
                    Host.setPassword(rs.getString("Password"));
                    Host.setFullname(rs.getString("fullname"));
                    Host.setGender(rs.getString("gender"));
                    Host.setDoB(new java.util.Date(rs.getDate("bod").getTime()));
                    Host.setPhone_number(rs.getInt("phone no"));
                    Host.setEmail(rs.getString("email"));
                    return Host;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Error retrieving Host record: " + e.getMessage(), e);
        }
        return null;
    }
}
